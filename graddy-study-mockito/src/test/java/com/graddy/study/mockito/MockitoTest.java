package com.graddy.study.mockito;

import static org.junit.Assert.assertEquals;
//Let's import Mockito statically so that the code looks clearer
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoTest {

	/**
	 * now you can verify interactions
	 * verify behavior
	 */
	@Test
	public void verify_behaviour(){
		//Mock create a list object
		List mock = mock(List.class);
		//use mock object
		mock.add(1);
		mock.clear();
		//verify if add(1) and clear() happen
		verify(mock).add(1);
		verify(mock).clear();
	}
	/**
	 * stub method calls
	 */
	@Test
	public void stuff_method_call() {
		// you can mock concrete classes, not only interfaces
		LinkedList mockedList = mock(LinkedList.class);

		// stubbing appears before the actual execution
		when(mockedList.get(0)).thenReturn("first");

		// the following prints "first"
		System.out.println(mockedList.get(0));
		assertEquals("first", mockedList.get(0));

		// the following prints "null" because get(999) was not stubbed
		System.out.println(mockedList.get(999));
		assertEquals(null, mockedList.get(999));
	}
	/**
	 * How about some stubbing?
	 */
	@Test
	public void some_stubbing() {
		//You can mock concrete classes, not just interfaces
		 LinkedList mockedList = mock(LinkedList.class);

		 //stubbing
		 when(mockedList.get(0)).thenReturn("first");
		 when(mockedList.get(1)).thenThrow(new RuntimeException());

		 //following prints "first"
		 System.out.println(mockedList.get(0));

		 //following throws runtime exception
		 System.out.println(mockedList.get(1));

		 //following prints "null" because get(999) was not stubbed
		 System.out.println(mockedList.get(999));

		 //Although it is possible to verify a stubbed invocation, usually it's just redundant
		 //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
		 //If your code doesn't care what get(0) returns, then it should not be stubbed. Not convinced? See here.
		 verify(mockedList).get(0);
	}
	
	/**
	 * Argument matchers
	 */
	@Test
	public void argument_matchers() {
		//mock creation
		 List mockedList = mock(List.class);
		 
		//stubbing using built-in anyInt() argument matcher
		 when(mockedList.get(anyInt())).thenReturn("element");

		 //stubbing using custom matcher (let's say isValid() returns your own matcher implementation):
		 //when(mockedList.contains(argThat(isValid()))).thenReturn("element");

		 //following prints "element"
		 System.out.println(mockedList.get(999));

		 //you can also verify using an argument matcher
		 verify(mockedList).get(anyInt());

		 //argument matchers can also be written as Java 8 Lambdas
		 //verify(mockedList).add(argThat(someString -> someString.length() > 5));
		 
	}
	/**
	 * Verifying exact number of invocations / at least x / never
	 */
	@Test
	public void verifying_exact_number_of_invocations() {
		//mock creation
		 List mockedList = mock(List.class);
		 
		//using mock
		 mockedList.add("once");

		 mockedList.add("twice");
		 mockedList.add("twice");

		 mockedList.add("three times");
		 mockedList.add("three times");
		 mockedList.add("three times");

		 //following two verifications work exactly the same - times(1) is used by default
		 verify(mockedList).add("once");
		 verify(mockedList, times(1)).add("once");

		 //exact number of invocations verification
		 verify(mockedList, times(2)).add("twice");
		 verify(mockedList, times(3)).add("three times");

		 //verification using never(). never() is an alias to times(0)
		 verify(mockedList, never()).add("never happened");

		 //verification using atLeast()/atMost()
		 verify(mockedList, atLeastOnce()).add("three times");
		 verify(mockedList, atLeast(2)).add("three times");
		 verify(mockedList, atMost(5)).add("three times");
	}
	/**
	 * Stubbing void methods with exceptions
	 */
	@Test
	public void stubbing_void_methods_with_exceptions() {
		//mock creation
		 List mockedList = mock(List.class);
		 
		 doThrow(new RuntimeException()).when(mockedList).clear();

	    //following throws RuntimeException:
	    mockedList.clear();
	}
	/**
	 * Verification in order
	 */
	@Test
	public void verification_in_order() {
		// A. Single mock whose methods must be invoked in a particular order
		 List singleMock = mock(List.class);

		 //using a single mock
		 singleMock.add("was added first");
		 singleMock.add("was added second");

		 //create an inOrder verifier for a single mock
		 InOrder inOrder = inOrder(singleMock);

		 //following will make sure that add is first called with "was added first", then with "was added second"
		 inOrder.verify(singleMock).add("was added first");
		 inOrder.verify(singleMock).add("was added second");

		 // B. Multiple mocks that must be used in a particular order
		 List firstMock = mock(List.class);
		 List secondMock = mock(List.class);

		 //using mocks
		 firstMock.add("was called first");
		 secondMock.add("was called second");

		 //create inOrder object passing any mocks that need to be verified in order
		 inOrder = inOrder(firstMock, secondMock);

		 //following will make sure that firstMock was called before secondMock
		 inOrder.verify(firstMock).add("was called first");
		 inOrder.verify(secondMock).add("was called second");

		 // Oh, and A + B can be mixed together at will
	}
	
	/**
	 * 模拟我们所期望的结果 
	 */
	@Test
	public void when_thenReturn(){
		//mock a Iterator Class
		Iterator iterator = mock(Iterator.class);
		when(iterator.next()).thenReturn("hello").thenReturn("world");
		String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
		assertEquals("hello world world",result);
	}
	@Test(expected = IOException.class)
	public void when_thenThrow() throws IOException {
		OutputStream outputStream = mock(OutputStream.class);
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		doThrow(new IOException()).when(outputStream).close();
		outputStream.close();
	}
	/**
	 * modify unstubb return default value
	 */
	@Test
	public void unstubbed_invocations(){
		//use answer to return default value
		List mock = mock(List.class,new Answer() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return 999;
			}
		});
		//下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值
		assertEquals(999, mock.get(1));
		//下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值
		assertEquals(999,mock.size());
	}
	//capture args to assert
    @Test
	public void capturing_args(){
		PersonDao personDao = mock(PersonDao.class);
		PersonService personService = new PersonService(personDao);
	
		ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
		personService.update(1,"jack");
		verify(personDao).update(argument.capture());
		assertEquals(1,argument.getValue().getId());
		assertEquals("jack",argument.getValue().getName());
	}
	
	 class Person{
		private int id;
		private String name;
	
		Person(int id, String name) {
			this.id = id;
			this.name = name;
		}
	
		public int getId() {
			return id;
		}
	
		public String getName() {
			return name;
		}
	}
	
	interface PersonDao{
		public void update(Person person);
	}
	
	class PersonService{
		private PersonDao personDao;
	
		PersonService(PersonDao personDao) {
			this.personDao = personDao;
		}
	
		public void update(int id,String name){
			personDao.update(new Person(id,name));
		}
	}
	/**
	 * real part mock
	 */
	@Test
	public void real_partial_mock() {
		// via spy to call real api
		List list = spy(new ArrayList());
		assertEquals(0, list.size());
		A a = mock(A.class);
		//via thenCallRealMethod to call real api
		when(a.doSomething(anyInt())).thenCallRealMethod();
		assertEquals(999, a.doSomething(999));
	}
	class A{
		public int doSomething(int i) {
			return i;
		}
	}
	/**
	 * reset mock
	 */
	@Test
	public void reset_mock() {
		List list = mock(List.class);
		when(list.size()).thenReturn(10);
		list.add(1);
		assertEquals(10, list.size());
		//reset mock lear all the when and interactive
		reset(list);
		assertEquals(0, list.size());
	}
	
	
	
	
	
	
	
	
	
}
