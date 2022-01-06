package ru.spbstu.telematics;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedHashSet<E> {
	
	private static final int DefaultCapacity = 16;
	private static final float DefaultLoadFactor = (float)0.75;
	private static final int MaxCapacity = 1 << 30;

	private static class Node<E>{
		
		private E data;//данные
		private Node<E> prev;//предыдущий элемент списка
		private Node<E> next;//следующий элемент списка
		private Node<E> tnext;//следующий элемент таблицы
		
		public Node(E data, Node<E> prev, Node<E> next, Node<E> tnext) {
			this.data = data;
			this.prev = prev;
			if(prev != null)
				prev.next = this;
			this.next = next;
			if(next != null)
				next.prev = this;
			this.tnext = tnext;
		}
		
		public Node<E> getTNext(){
			return this.tnext;
		}
		
		public E getData() {
			return this.data;
		}
		
		public Node<E> getPrev(){
			return this.prev;
		}
		
		public Node<E> getNext() {
			return this.next;
		}
		
		public void setTNext(Node<E> tnext) {
			this.tnext = tnext;
		}
		
		public void remove() {
			this.prev.next = this.next;
			this.next.prev = this.prev;
		}
		
		public int hash() {
			if(data != null)
				return this.data.hashCode();
			else return 0;
		}
	}
	
	private Node<E>[] table;
	private Node<E> head = new Node(null, null,null,null);
	private Node<E> tail = new Node(null, head, null, null);
	private int size = 0;
	private int capacity;
	private float loadFactor;
	
	public MyLinkedHashSet() {
		this.capacity = DefaultCapacity;
		this.loadFactor = DefaultLoadFactor;
		this.table = new Node[this.capacity];
	}
	
	public MyLinkedHashSet(int initialCapacity) {
		if(initialCapacity < 0)
			throw new IllegalArgumentException();
		this.capacity = 1;
		while(this.capacity < initialCapacity && this.capacity < MaxCapacity) //емкость хэш-таблицы = степень 2
			this.capacity *= 2;
		this.loadFactor = DefaultLoadFactor;
		this.table = new Node[this.capacity];
	}
	
	public MyLinkedHashSet(int initialCapacity, float loadFactor) {
		if(initialCapacity < 0 || loadFactor <= 0)
			throw new IllegalArgumentException();
		this.capacity = 1;
		while(this.capacity < initialCapacity && this.capacity < MaxCapacity) //емкость хэш-таблицы = степень 2
			this.capacity *= 2;
		this.loadFactor = loadFactor;
		this.table = new Node[this.capacity];
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean add(E element) {
		int hash = 0;
		if(element != null)
			hash = element.hashCode() & (capacity - 1); //нельз€ заходить за границы массива

		Node<E> curr = table[hash];
		if(curr == null) //если по этому хэшу еще нет значений
		{
			table[hash] = new Node(element,tail.getPrev(), tail, null);
			size++;
			checkTable();
			return true;
		}
		else //если по этому хэшу уже лежат значени€
		{
			Node<E> par = curr;
			while(curr != null)
			{
				if(curr.getData() == element || curr.getData().equals(element))
					return false; //уже присутствующие элементы не добавл€ютс€
				par = curr;
				curr = curr.getTNext();
			}
			curr = new Node(element,tail.getPrev(),tail,null);
			par.setTNext(curr);
			size++;
			checkTable();
			return true;
		}
	}
	
	public boolean remove(E element) {
		int hash = 0;
		if(element != null)
			hash = element.hashCode() & (capacity - 1);
		Node<E> curr = table[hash];
		Node<E> par = curr;
		while(curr != null)
		{
			if(curr.getData() == element || curr.getData().equals(element)) //элемент найден
			{
				if(par.equals(curr)) //если найденный элемент - первый элемент в хэш-таблице
				{
					table[hash] = curr.getTNext();
					curr.remove();
					size--;
					return true;
				}
				else
				{
					par.setTNext(curr.getTNext());
					curr.remove();
					size--;
					return true;
				}
			}
			else //элемент не найден
			{
				par = curr;
				curr = curr.getTNext();
			}
		}
		return false;
	}
	
	private void checkTable() {
		if((int) (capacity*loadFactor) <= size)
		{
			int newCapacity = 2*capacity;
			Node<E>[] newTable = new Node[newCapacity];
			Node<E> curr = head.getNext();
			while(curr.getNext() != null)
			{
				int hash = 0;
				if(curr != null)
					hash = curr.hash() & (newCapacity - 1);
				if(newTable[hash] == null)
				{
					curr.setTNext(null);
					newTable[hash] = curr;
				}
				else
				{
					Node<E> sub_curr = newTable[hash];
					Node<E> par = sub_curr;
					while(sub_curr != null)
					{
						par = sub_curr;
						sub_curr = sub_curr.getTNext();
					}
					curr.setTNext(null);
					par.setTNext(curr);
				}
				curr = curr.getNext();
			}
			table = newTable;
			capacity = newCapacity;
		}
	}
	
	public boolean contains(E element) {
		int hash = 0;
		if(element != null)
			hash = element.hashCode() & (capacity - 1);
		Node<E> curr = table[hash];
		while(curr != null)
		{
			if(curr.getData() == element || curr.getData().equals(element))
				return true;
			else
			{
				curr = curr.getTNext();
			}
		}
		return false;
	}
	
	public boolean isEmpty() {
		return (size == 0);
	}
	
	public void clear() {
		for(int i = 0; i < capacity; i++)
		{
			table[i] = null;
		}
		head = new Node(null,null,null,null);
		tail = new Node(null,head,null,null);
		size = 0;
	}
	
	public final Iterator<E> iterator()
	{
		return new MyIterator();
	}
	
	final class MyIterator implements Iterator<E> {
		Node<E> next;
		Node<E> current;
		
		MyIterator() {
			current = null;
			next = head.getNext();
		}
		
		public boolean hasNext() {
			return !next.equals(tail);
		}
		
		public E next() {
			if(next.equals(tail))
				throw new NoSuchElementException();
			current = next;
			next = next.getNext();
			return current.getData();
		}
		
		public void remove() {
			if(current == null)
				throw new IllegalStateException();
			int hash = current.hash() & (capacity - 1);
			if(table[hash].equals(current))
			{
				table[hash] = current.getTNext();
				current.remove();
			}
			else
			{
				Node<E> par = table[hash];
				while(!par.getTNext().equals(current))
				{
					par = par.getTNext();
				}
				par.setTNext(current.getTNext());
				current.remove();
			}
			current = null;
			size--;
		}
	}
}