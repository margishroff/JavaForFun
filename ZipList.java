
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mshroff
 */
public class ZipList {
    public static class LinkedListNode{
        int val;
        LinkedListNode next;
    
        LinkedListNode(int node_value) {
            val = node_value;
            next = null;
        }
    };
    
    public static LinkedListNode _insert_node_into_singlylinkedlist(LinkedListNode head, LinkedListNode tail, int val){
        if(head == null) {
            head = new LinkedListNode(val);
            tail = head;
        }
        else {
            tail.next = new LinkedListNode(val);
            tail = tail.next;
        }
        return tail;
    }
    static LinkedListNode Zip(LinkedListNode pList) {
     
        //first find size
        LinkedListNode pcurrNode = pList;
        
        int size = findListSize(pList);
        System.out.println("Total Size == " + size);
         
        LinkedListNode pCurr = pList;
        LinkedListNode pNewHead = null;
        LinkedListNode pNewHead2 = null;
        LinkedListNode pNewTail = null;
        LinkedListNode pPrev = null;
        int counter = 0 ; 
        
        pNewTail = _insert_node_into_singlylinkedlist(pNewHead, pNewTail,pList.val);
        pNewHead = pNewTail;
        while( counter < size/2){ 
            
            pNewTail = pCurr.next;
            pNewTail = _insert_node_into_singlylinkedlist(pNewHead,pNewTail,pCurr.val);
            counter++;
        }
        pCurr = pCurr.next;
        
        while( counter < size){ 
            pPrev = pCurr;
            pCurr = _insert_node_into_singlylinkedlist(pNewTail,pNewHead2,pCurr.val);
            pCurr = pCurr.next;
            
            counter++;
        }
        
         System.out.println("New List ") ; 
            printList(pNewHead); 

        //end the first list
        LinkedListNode pReversedHead = reverseList(pNewHead); 
       
        LinkedListNode currNew = pList;
         LinkedListNode newHead = currNew;
        System.out.println("New reversed head == " + pReversedHead.val); 

        while(pList != null && pReversedHead != null){
            
            currNew = pList; 
            pList = pList.next;
             System.out.print( currNew.val + " -> "); 
            currNew.next = pReversedHead;
          //  System.out.println( pReversedHead.val);
            pReversedHead = pReversedHead.next;
            
            
        }

         return newHead;

    }
    
    static void printList(LinkedListNode head){
        while(head!= null){
            if(head.val != 0){
                System.out.print(head.val + " ->");
            }
            
            head = head.next;
        }
    }
    static int findListSize(LinkedListNode node){

        int size = 0;
        LinkedListNode counterNode = node;

        while(counterNode != null){
            size++;
            counterNode = counterNode.next;   
        }
        return size;
    }
 
public static LinkedListNode reverseList(LinkedListNode head) {
    if(head==null || head.next == null)
        return head;
 
    //get second node    
    LinkedListNode second = head.next;
    //set first's next to be null
    head.next = null;
 
    LinkedListNode rest = reverseList(second);
    second.next = head;
 
    return rest;
}
    public static void main(String[] args) throws IOException {
        
        LinkedListNode head = new LinkedListNode(1);
        LinkedListNode curr = head;
        LinkedListNode second = new LinkedListNode(2);
        head.next = second;
         
        LinkedListNode third = new LinkedListNode(3);
        second.next = third; 
        LinkedListNode forth = new LinkedListNode(4);
        third.next = forth;
        
        LinkedListNode fifth = new LinkedListNode(5);
        forth.next = fifth;
       
        LinkedListNode sixth = new LinkedListNode(6);
        fifth.next = sixth;
         

        printList(head);
        head = Zip(head);
        printList(head);
    }
}
