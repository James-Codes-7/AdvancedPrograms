package bill;

import java.util.ArrayList;
import java.util.Scanner;

public class BillingSystem {

	static Scanner scan=new Scanner(System.in);
	static int bill_id=0;
	public static void main(String[] args) {
		ArrayList<Bill_list> billList=new ArrayList<Bill_list>();
		ArrayList<Customer_details>  customerDetails=new ArrayList<Customer_details>();
		ArrayList<Item_detail> itemDetails=new ArrayList<Item_detail>();

		itemDetails.add(new Item_detail(1,"munch",5,100));
		itemDetails.add(new Item_detail(2,"kit  ",5,100));
		itemDetails.add(new Item_detail(3,"star ",5,100));
		itemDetails.add(new Item_detail(4,"treat",5,100));

		customerDetails.add(new Customer_details(1,"john"));
		customerDetails.add(new Customer_details(2,"roone"));
		customerDetails.add(new Customer_details(3,"bond"));
		customerDetails.add(new Customer_details(4,"vinot"));

		//if bill not create we cannot go another options
		int  billEmpty=0,check=1;
		//loop run until customer give another number 
		while(check==1)
		{
			System.out.println("1 Bill Create");
			System.out.println("2 Search bill");
			System.out.println("3.Search customerBill");
			System.out.println("4.Item Stack Deatil");
			System.out.println("5 Search Item's Bill");
			System.out.println("6 Add Customer");
			System.out.println("7 Bill List");
			System.out.println("Enter Option");
			int options=scan.nextInt();
			switch(options)
			{
			//bill the customer items and show the bill
			case 1:billCreate(customerDetails,itemDetails,billList);
			billEmpty++;
			break;
			case 2:if(billEmpty!=0)
			{
				//show bill based on bill id
				searchBill(billList);
			}
			break;
			case 3:if(billEmpty!=0)
				//base on customer id
				searchCustomerBill(billList);
			break;
			//enter the item id show what item stack details you want 
			case 4:itemStack(itemDetails);
			break;
			//show bills based on item id
			case 5:itemBills(billList);
			break;
			//adding a new customer
			case 6:customerAdd(customerDetails);
			break;
			// display the full bills in the bill list
			case 7:fullBilList(billList);
			break;
			}
			System.out.println("do u want continue press 1");
			check=scan.nextInt();
		}
	}
	public static void billCreate(ArrayList<Customer_details>  customerDetails,ArrayList<Item_detail> itemDetails,ArrayList<Bill_list> billList)
	{
		int tillPurchase=1;
		// it show all the customers
		for(Customer_details tempCustomerDetails:customerDetails)
		{
			System.out.println(tempCustomerDetails.customerId+"."+tempCustomerDetails.customerName);
		}
		//you enter the customer id
		System.out.println("Enter the customer Id");
		int tempCustomerId=scan.nextInt();
		//it take for give customer name and id to bill list
		Customer_details takeCustomerDetails=customerDetails.get(tempCustomerId-1);
		int lineTotalAmount=0;
		//provide unique bill id
		bill_id++;
		int tempTotalAmount=0;
		ArrayList<Bill_Line_Details> itemLine=new ArrayList<Bill_Line_Details>();
		do {
			lineTotalAmount=0;
			//it show all the items
			for(Item_detail items:itemDetails)
			{
				System.out.println(items.itemId+" "+items.itemName+" "+items.iteprice);

			}
			System.out.println("Which product you want enter id number");
			int tempItemId=scan.nextInt();
			//it take that enter item
			Item_detail tempItemDetail=itemDetails.get(tempItemId-1);
			System.out.println("Enter the quentity of the product");
			int usersQuentity=scan.nextInt();
			//if not stack suddenly import the item
			if(tempItemDetail.itemQuantity-usersQuentity<0)
				tempItemDetail.itemQuantity+=(2*usersQuentity);
			
			tempItemDetail.itemQuantity-=usersQuentity;
			//calculate line amount item price*quantity
			lineTotalAmount+=tempItemDetail.iteprice*usersQuentity;
			
			//calculate total one bill amount
			tempTotalAmount+=lineTotalAmount;
			//it is one item line in bill
			itemLine.add(new Bill_Line_Details(tempItemDetail.itemId,tempItemDetail.itemName,tempItemDetail.iteprice,usersQuentity,lineTotalAmount));
			System.out.println("Do u continue press 1");
			tillPurchase=scan.nextInt();

		}while(tillPurchase==1);
         //display bill
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("                 Bill id:"+bill_id+"               ");
		System.out.println("                 Date:"+java.time.LocalDate.now()+"    ");
		System.out.println("                 Customer Name:"+takeCustomerDetails.customerName+"        ");
		System.out.println("Item id     Item name    Item price  Item quentity    Item amount");
		display(itemLine);
		billList.add(new Bill_list(takeCustomerDetails.customerId,takeCustomerDetails.customerName,bill_id,tempTotalAmount,itemLine));

	}
	public static void searchBill(ArrayList<Bill_list> billList) {
		//show bill id based on bill id
		System.out.println("Enter the bill id");
		int tempBillId=scan.nextInt();
		for(Bill_list bills:billList)
		{
			if(bills.bill_Id==tempBillId)
			{
				System.out.println("--------------------------------------------------------------------------------------");
				System.out.println("                 Bill id:"+tempBillId+"               ");
				System.out.println("                 Date:"+java.time.LocalDate.now()+"    ");
				System.out.println("                 Customer Name:"+bills.billCustomername+"        ");
				System.out.println("Item id     Item name    Item price  Item quentity    Item amount");
				display(bills.billItemLines);
			}
		}

	}
	public static void searchCustomerBill(ArrayList<Bill_list> billList) {

		//show bill base on customer id
		System.out.println("enter the customer id");
		int checkCustomerId=scan.nextInt();
		for(Bill_list bills:billList)
		{
			if(bills.billCustomerid==checkCustomerId)
			{
				System.out.println("--------------------------------------------------------------------------------------");
				System.out.println("                 Bill id:"+bills.bill_Id+"               ");
				System.out.println("                 Date:"+java.time.LocalDate.now()+"    ");
				System.out.println("                 Customer Name:"+bills.billCustomername+"        ");
				System.out.println("Item id     Item name    Item price  Item quentity    Item amount");
				display(bills.billItemLines);
				System.out.println("--------------------------------------------------------------------------------------");
			}
		}

	}
	public static void itemStack(ArrayList<Item_detail> itemsList)
	{
		// check the ItemStack
		// show item details based on item id
		System.out.println("Enter the item id:");
		int checkItemId=scan.nextInt();
		for(Item_detail items:itemsList)
		{
			if(items.itemId==checkItemId)
			{
				System.out.println("The quentity of:"+items.itemName+" is:"+items.itemQuantity);
			}
		}

	}
	public static void itemBills(ArrayList<Bill_list> billList)
	{
		//show bill based on item id
		System.out.println("enter the item id");
		int tempItemID=scan.nextInt();
		for(Bill_list bills:billList)
		{
			
			for(Bill_Line_Details billLine:bills.billItemLines)
			{
				if(billLine.billItemId==tempItemID)
				{
					System.out.println("--------------------------------------------------------------------------------------");
					System.out.println("                 Bill id:"+bills.bill_Id+"               ");
					System.out.println("                 Date:"+java.time.LocalDate.now()+"    ");
					System.out.println("                 Customer Name:"+bills.billCustomername+"        ");
					System.out.println("Item id     Item name    Item price  Item quentity    Item amount");
					//all the item line in the bill like
					//id   name  price   quantity    total price
					// 1  munch  5        10         50
					display(bills.billItemLines);
					System.out.println("--------------------------------------------------------------------------------------");
				}
			}
		}
	}
	public static void customerAdd(ArrayList<Customer_details>  customerDetails)
	{
		System.out.println("enter the customer name");
		scan.nextLine();
		//enter new customer name
		String customerName=scan.nextLine();
		//provide new customer id for this new user 
		int newId=1+customerDetails.size();;
		customerDetails.add(new Customer_details(newId,customerName));
	}


	public static void fullBilList(ArrayList<Bill_list> billList)
	{
		for(Bill_list bill:billList)
		{
			System.out.println("--------------------------------------------------------------------------------------");
			System.out.println("                 Bill id:"+bill.bill_Id+"               ");
			System.out.println("                 Date:"+java.time.LocalDate.now()+"    ");
			System.out.println("                 Customer Name:"+bill.billCustomername+"        ");
			System.out.println("Item id     Item name    Item price  Item quentity    Item amount");
			display(bill.billItemLines);
			System.out.println("--------------------------------------------------------------------------------------");
		}
	}



	static void display(ArrayList<Bill_Line_Details> billLines)
	{
		//it is total one bill amount
		int tempBillAmount=0;
		//it print the items detail in the bill
		for(Bill_Line_Details line:billLines)
		{
			System.out.println(line.billItemId+"            "+line.billItemName+"              "+line.price+"              "+line.quentity+"                "+line.totallineAmout);
			tempBillAmount+=line.totallineAmout;
		}
		System.out.println("TOTAL BILL AMOUNT :"+tempBillAmount);
	}
}

class Item_detail
{
	int itemId;
	String  itemName;
	int iteprice;
	int itemQuantity;
	Item_detail(int a,String b,int c,int d)
	{
		this.itemId=a;
		this.itemName=b;
		this.iteprice=c;
		this.itemQuantity=d;


	}
}
class Customer_details
{
	int customerId;
	String customerName;
	Customer_details(int a,String b)
	{
		this.customerId=a;
		this.customerName=b;
	}
}
class Bill_Line_Details
{
	int billItemId;
	String billItemName;
	int price;
	int quentity;
	int totallineAmout;
	Bill_Line_Details(int id,String name,int price,int quantity,int totalAmount)
	{
		this.billItemId=id;
		this.billItemName=name;
		this.price=price;
		this.quentity=quantity;
		this.totallineAmout=totalAmount;
	}

}
class Bill_list
{
	int billCustomerid;
	String billCustomername;
	int bill_Id;
	long totalBillAmount=0;
	ArrayList<Bill_Line_Details> billItemLines;
	Bill_list(int cusId,String name,int billid,int totalBillAmount,ArrayList<Bill_Line_Details> billLines)
	{
		this.billCustomerid =cusId;
		this.billCustomername=name;
		this.bill_Id=billid;
		this.totalBillAmount=totalBillAmount;
		this.billItemLines=billLines;
	}



}

