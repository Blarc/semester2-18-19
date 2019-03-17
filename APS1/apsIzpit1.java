//tu bo rezultat
LinkedList list = new LinkedList<Integer>();
//tu bodo neobdelana vozlišča
Queue q = new Queue<Node>();
q.add(firstNode);

public static void fri(LinkedList list, Queue q) {
	//pollamo prvo vozlišče iz vrste
	Node node = q.poll();
	if (node == null) {
		return;
	}
	//dodamo vozlišče v rešitev
	list.add(node.key);
	//dodamo v vrsto njegove sinove
	q.add(node.left);
	q.add(node.right);
	//rekurzivni klic z novo vrsto
	fri(list, queue);
}