package main;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
	private static LRUNode headNode;
	private static LRUNode tailNode;
	private static int MAX_SIZE=5;
	private static Map<Integer, LRUNode> map;
	public static void main(String[] args) {
		populateMap();
		showOrder();
		boolean result = fetchFromCache(4);
		System.out.println(result?"Hit":"Miss");
		showOrder();
		result = fetchFromCache(6);
		System.out.println(result?"Hit":"Miss");
		showOrder();
		//to check case where size is not equal to max size
		LRUNode node = map.get(1);
		node.next.prev=node.prev;
		node.prev.next=node.next;
		map.remove(1);
		result = fetchFromCache(9);
		System.out.println(result?"Hit":"Miss");
		showOrder();
	}

	private static boolean fetchFromCache(int i) {
		boolean result = false;
		if(map.containsKey(i)) {
			//it's a hit
			result = true;
		}
		else {
			//it's a miss, fetch from source
		}
		rearrangeCache(i);
		return result;
	}

	private static void rearrangeCache(int i) {
		if(map.get(i)!=null) {
			LRUNode node = map.get(i);
			if(node!=headNode) {
				if(node==tailNode) {
					tailNode=node.prev;
					node.prev.next=null;
				}
				else {
					node.next.prev=node.prev;
					node.prev.next=node.next;
				}
				node.next=headNode;
				headNode.prev=node;
				headNode=node;
			}
		}
		else {
			if(map.size()==MAX_SIZE) {
				tailNode = tailNode.prev;
				tailNode.next.prev=null;
				tailNode.next = null;
			}
			LRUNode newNode = new LRUNode();
			newNode.value=i;
			headNode.prev=newNode;
			newNode.next=headNode;
			headNode=newNode;
		}
	}

	private static void showOrder() {
		LRUNode node = headNode;
		while(node.next!=null) {
			System.out.print(node.value + "->");
			node=node.next;
		}
		System.out.println(node.value);
	}

	private static void populateMap() {
		map = new HashMap<Integer, LRUNode>();
		LRUNode node1 = new LRUNode();
		node1.value=1;
		
		LRUNode node2 = new LRUNode();
		node2.value=2;
		
		LRUNode node3 = new LRUNode();
		node3.value=3;
		
		LRUNode node4 = new LRUNode();
		node4.value=4;
		
		LRUNode node5 = new LRUNode();
		node5.value=5;
		
		headNode=node1;
		node1.next=node2;
		node2.prev=node1;
		node2.next=node3;
		node3.prev=node2;
		node3.next=node4;
		node4.prev=node3;
		node4.next=node5;
		node5.prev=node4;
		tailNode=node5;
		
		map.put(1, node1);
		map.put(2, node2);
		map.put(3, node3);
		map.put(4, node4);
		map.put(5, node5);
	}
	
}

class LRUNode {
	int value;
	LRUNode prev;
	LRUNode next;
}