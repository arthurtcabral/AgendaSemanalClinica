
public class SinglyLinkedList<E> implements List<E> {
	Node<E> head;
	Node<E> tail;
	int numElements;

	public SinglyLinkedList() {
		head = tail = null;
		numElements = 0;
	}

	public int numElements() {
		return numElements;
	}

	public boolean isEmpty() {
		return numElements == 0;
	}

	public boolean isFull() {
		return false;
	}

	public void insertFirst(E element) {
		Node<E> newNode = new Node<E>(element);
		if (isEmpty())
			head = tail = newNode;
		else {
			newNode.setNext(head);
			head = newNode;
		}

		numElements++;
	}

	public void insertLast(E element) {
		Node<E> newNode = new Node<E>(element);
		if (isEmpty())
			head = tail = newNode;
		else {
			tail.setNext(newNode);
			tail = newNode;
		}

		numElements++;
	}

	public void insert(E element, int pos) {
		if (pos < 0 || pos > numElements)
			throw new IndexOutOfBoundsException();

		if (pos == 0)
			insertFirst(element);
		else if (pos == numElements) 
			insertLast(element);
		else {
			Node<E> prev = head;
			for (int i = 0; i < pos - 1; i++)
				prev = prev.getNext();

			Node<E> newNode = new Node<E>(element);
			newNode.setNext(prev.getNext());
			prev.setNext(newNode);
			numElements++;
		}
	}

	public E removeFirst() {
		if (isEmpty())
			throw new UnderflowException();

		E element = head.getElement();

		if (head == tail)
			head = tail = null;
		else
			head = head.getNext();

		numElements--;
		return element;
	}

	public E removeLast() {
		if (isEmpty())
			throw new UnderflowException();

		E element = tail.getElement();

		if (head == tail)
			head = tail = null;
		else {
			Node<E> prev = head;
			while (prev.getNext() != tail)
				prev = prev.getNext();

			tail = prev;
			prev.setNext(null);
		}

		numElements--;
		return element;
	}

	public E remove(int pos) {
		if (pos < 0 || pos >= numElements)
			throw new IndexOutOfBoundsException();

		if (pos == 0)
			return removeFirst();
		else if (pos == numElements - 1)
			return removeLast();
		else {
			Node<E> prev = head;
			for (int i = 0; i < pos - 1; i++)
				prev = prev.getNext();

			E element = prev.getNext().getElement();

			prev.setNext(prev.getNext().getNext());

			numElements--;
			return element;
		}
	}

	public E get(int pos) {
		if (pos < 0 || pos >= numElements)
			throw new IndexOutOfBoundsException();

		Node<E> current = head;
		for (int i = 0; i < pos; i++)
			current = current.getNext();

		return current.getElement();
	}

	public int search(E element) {
		Node<E> current = head;
		int i = 0;
		while (current != null) {
			if (element.equals(current.getElement()))
				return i;
			i++;
			current = current.getNext();
		}

		return -1;
	}

	public String toString() {
		String s = "";

		Node<E> current = head;
		while (current != null) {
			s += current.getElement().toString() + " ";
			current = current.getNext();
		}
		return s;
	}
}