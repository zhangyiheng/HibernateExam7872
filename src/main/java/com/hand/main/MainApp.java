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
		customer.setStore_id(1);
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
			for(Address address:list){
				if(address.getAddress_id()==addressId){
					bool = true;
				}
			}
			if(bool!=true){
				System.out.println("你输入的ID不存在，请重新输入");
				addressId= input.nextInt();
			}
			System.out.println("------------------");
		}while(bool);

		
		
		customer.setFirst_name(firstname);
		customer.setLast_name(lastName);
		customer.setEmail(email);
		customer.setAddress_id(addressId);
		customer.setCreate_date(new Date());
		
		
		

		
		session.save(customer);
		Address address = (Address) session.get(Address.class, addressId);
		transaction.commit();
		System.out.println("已经保存的数据如下");
		System.out.println("ID:"+customer.getCustomer_id());
		System.out.println("Firstname:"+customer.getFirst_name());
		System.out.println("lastname:"+customer.getLast_name());
		System.out.println("Email:"+customer.getEmail());
		System.out.println("address:"+address.getAddress());
		
		session.close();
	}

}
