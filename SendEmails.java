/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author sisingh
 */
public class SendEmails {

    private final String databaseType = "mssql";
    private String env = "dev";
    private static HashMap<String, SendEmails> theInstances = new HashMap<>();
    GenericJdbc db = null;
    private String emailTo = ""; 
    private String managerName = ""; 
    private String approverName = "";
    private String planName = "";
    private String planCommnets = "";
    private String plaStatus = ""; 
    private int headcount_delta = 0;
    private String envDetails = "";

    public SendEmails(String env) {
        this.env = env;
        db = GenericJdbc.getInstance("mssql", env);
        Props p = new Props();
        p.load();
        env = p.getProperty("sa2env");

    }

    public static SendEmails getInstance(String env) {
        if (!theInstances.containsKey(env)) {
            theInstances.put(env, new SendEmails(env));
        }
        return theInstances.get(env);
    }

    public boolean sendMailToApprover(String mgrId, String testEmail) throws MessagingException, AddressException, SendFailedException, UnsupportedEncodingException {
        boolean success = false;
        try {
            envDetails = this.env;
            String inputLine = "";
            String templateLable = "plansubmit";

            ArrayList<EmployeeDetailObject> items = Controller.getInstance().getPlanApproverEmail(mgrId);
            for (EmployeeDetailObject g : items) {
                emailTo = g.getEmailApprover();
                managerID = g.getManagerID();
                managerName = g.getManagerName();
                managerEmail = g.getManagerEmail();
                approverID = g.getApproverID();
                approverName = g.getApproverName(); 
            }

            // first & last name code
            String[] nameManager = managerName.split(",");
            managerName = nameManager[1] + "  " + nameManager[0];

            String[] nameApprover = approverName.split(",");
            approverName = nameApprover[1] + "  " + nameApprover[0];

            ArrayList<PlanObject> planDetails = Controller.getInstance().getPlanList(mgrId);

 
            PlanObject p = planDetails.get(0); // the plan that was just submitted. It's data needs to be pulled.
            planName = p.getPlanName();
            headcount_delta = p.getSummary().getHeadcount_delta();
          

            ArrayList<EmployeeDetailObject> result = Controller.getInstance().getTemplateEmail(templateLable);
            for (EmployeeDetailObject paData : result) {
                inputLine = paData.getTemplateDetail();
            }

            String headcountDeltaValue = String.valueOf(headcount_delta);
            if (headcount_delta > 0) {
                headcountDeltaValue = "+" + String.valueOf(headcount_delta);
            }

            inputLine = inputLine.replaceAll("#name#", approverName);
            inputLine = inputLine.replaceAll("#submitterName#", managerName);
            inputLine = inputLine.replaceAll("#planName#", planName);
         

            //Read HTML file END
            System.out.println("Sending mail...");
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", "mailhost.company.com");
            javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, null);
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);

            message.setSubject("Site Analyzer: Plan submitted by " + managerName);
            message.setFrom(new InternetAddress("me@company.com", "no-reply"));
            message.setContent(inputLine, "text/html");

            if (testEmail.length() > 0 && (envDetails.equals("dev") || envDetails.equals("qa"))) {
                //for testing 
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(testEmail));
            } else if (envDetails.equals("prod")) {
                // Email Details RecipientType TO
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
//                // Email Details RecipientType CC
//                message.addRecipient(Message.RecipientType.CC,new InternetAddress(managerEmail));
            }
            if (testEmail.isEmpty() && (envDetails.equals("dev") || envDetails.equals("qa"))) {
                success = true;
            } else {
                transport.connect();
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                success = true;
            }
        } catch (AddressException e) {
            throw new AddressException("[sendEmail]: Error in method " + e.getMessage());
        } catch (SendFailedException e) {
            throw new SendFailedException("[sendEmail]:Error in method " + e.getMessage());
        } catch (MessagingException e) {
            throw new MessagingException("[sendEmail]: Error in method " + e.getMessage());
        }
        return success;
    }

    public boolean sendMailToManagers(String mgrId, String planId, String  updatedBy, String testEmail) throws MessagingException, AddressException, SendFailedException, UnsupportedEncodingException {
        boolean success = false;
        try {
            envDetails = this.env;
            String templateLable = "planapprove";
            String inputLine = "";

            Person mgr = Controller.getInstance().getEmployeeEmailDetails(mgrId);
            managerID = mgrId;
            managerName = mgr.getName();
            managerEmail = mgr.getEmail();
            
            Person approver = Controller.getInstance().getEmployeeEmailDetails(updatedBy);
            approverID = approver.getId();
            approverName = approver.getName();
            emailTo = approver.getEmail();
            
            // first & last name code
            String[] nameManager = managerName.split(",");
            managerName = nameManager[1] + "  " + nameManager[0];

            String[] nameApprover = approverName.split(",");
            approverName = nameApprover[1] + "  " + nameApprover[0];

            ArrayList<PlanObject> planDetails = Controller.getInstance().getPlanList(mgrId);
            for (PlanObject p : planDetails) {
                //Extra check 
                if (planId.equalsIgnoreCase(String.valueOf(p.getPlanId()))) {
                    headcount_delta = p.getSummary().getHeadcount_delta(); 
                }
            }

            ArrayList<PlanObject> planStatus = Controller.getInstance().getPlanStatus(mgrId, planId);
            for (PlanObject ps : planStatus) {
                planName = ps.getPlanName();
                planCommnets = ps.getComments();
                plaStatus = ps.getStatus();
            }

            ArrayList<EmployeeDetailObject> result = Controller.getInstance().getTemplateEmail(templateLable);
            for (EmployeeDetailObject paData : result) {
                inputLine = paData.getTemplateDetail();
            }

            String headcountDeltaValue = String.valueOf(headcount_delta);
            if (headcount_delta > 0) {
                headcountDeltaValue = "+" + String.valueOf(headcount_delta);
            }

            inputLine = inputLine.replaceAll("#name#", managerName); 
            inputLine = inputLine.replaceAll("#planName#", planName);
            inputLine = inputLine.replaceAll("#headCountDelta#", headcountDeltaValue);
 
            inputLine = inputLine.replaceAll("#comments#", planCommnets);
 

            System.out.println("Sending mail...");
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", "mailhost.company.com");
            javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, null);
            Transport transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Site Analyzer: " + planName + " has been " + plaStatus);
            message.setFrom(new InternetAddress("me@company.com", "no-reply"));
            message.setContent(inputLine, "text/html");

            if (testEmail.length() > 0 && (envDetails.equals("dev") || envDetails.equals("qa"))) {
                //for testing 
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(testEmail));
            } else if (envDetails.equals("prod")) {
                // Email Details RecipientType TO
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
//                // Email Details RecipientType CC
//                message.addRecipient(Message.RecipientType.CC,new InternetAddress(managerEmail));
            }
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            success = true;
        } catch (AddressException e) {

            throw new AddressException("[sendEmail]: Error in method " + e.getMessage());

        } catch (SendFailedException e) {

            throw new SendFailedException("[sendEmail]:Error in method " + e.getMessage());

        } catch (MessagingException e) {
            throw new MessagingException("[sendEmail]: Error in method " + e.getMessage());
        }
        return success;
    }

}
