package com.hand.main;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.hand.bean.Address;
import com.hand.bean.Customer;
import com.hand.bean.Store;

public class MainApp {
	private SessionFactory factory;//一个session工厂
	private Session session;//一个会话
	private Transaction transaction;//事务

	public static void main(String[] args) {
		SessionFactory factory;//一个session工厂
		Session session;//一个会话
		Transaction transaction;//事务
		//创建配置对象
		Configuration config = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		//创建会话工厂对象
		factory = config.buildSessionFactory(serviceRegistry);
		//会话对象
		session = factory.openSession();
		//开启事务
		transaction = session.beginTransaction();
		// TODO Auto-generated method stub
		Customer customer = new Customer();
		Scanner input = new Scanner(System.in);
		System.out.println("请输入FirstName");
		String firstname = input.next();
		System.out.println("请输入lastName");
		String lastName = input.next();
		System.out.println("请输入email");
		String email = input.next();
		System.out.println("请输入Address ID");
		int addressId = input.nextInt();
		Boolean bool = false;
//		Address address = new Address();
		String hql = "from Address";
		Query query = session.createQuery(hql);
		List<Address> list = query.list();
		do{
			System.out.println("------------------1");
			for(Address address:list){
				System.out.println("------------------2");
				if(address.getAddress_id()==addressId){
					System.out.println("------------------3");
					bool = true;
					break;
				}
			}
			if(bool!=true){
				System.out.println("------------------4");
				System.out.println("你输入的ID不存在，请重新输入");
				addressId= input.nextInt();
			}
		}while(bool==false);

		
		
		customer.setFirst_name(firstname);
		customer.setLast_name(lastName);
		customer.setEmail(email);
		Address address = new Address();
		address.setAddress_id(addressId);
		customer.setAddress(address);
		customer.setCreate_date(new Date());
		Store store = new Store();
		store.setStore_id(1);
		customer.setStore(store);
		
		
		

		
		session.save(customer);
		Address address2 = (Address) session.get(Address.class, addressId);
		transaction.commit();
		System.out.println("已经保存的数据如下");
		System.out.println("ID:"+customer.getCustomer_id());
		System.out.println("Firstname:"+customer.getFirst_name());
		System.out.println("lastname:"+customer.getLast_name());
		System.out.println("Email:"+customer.getEmail());
		System.out.println("address:"+address2.getAddress());
		
		System.out.println("请输入要删除的CustomerID:");
		int customerId = input.nextInt();
		customer.setCustomer_id(customerId);
		session.delete(customer);
		transaction.commit();
		System.out.println("已删除"+customerId);
		
		session.close();
	}

}
