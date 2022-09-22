package giftgard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GiftCard {

	public static long accountNumber=1;
	public static int Counts=0;
	public static long giftCardNumber=1;
	public static long pin=1000;
	public static boolean iterate=true;
	static Scanner scan=new Scanner(System.in);
	public static void main(String[] args) {

		HashMap<Long,CustomerDetails> bankAccount=new HashMap<>();


		HashMap<Long,GiftCardDetails>  giftDetails=new HashMap<>();

		while(iterate)
		{
			System.out.println();

			System.out.println(" New Account    press 1   ");
			System.out.println(" Login          press 2   ");
			System.out.println(" Exit           press 3   ");

			System.out.println();
			System.out.println("Enter the option");
			int option=scan.nextInt();
			if(option==3) {iterate=false;continue;}
			switch(option)
			{
			case 1:newAccount(bankAccount);
			break;
			case 2:if(Counts!=0) {
				login(bankAccount,giftDetails);}
			break;
			}
			iterate=true;
			System.out.println("Do u continue    Press 1");
			option=scan.nextInt();
			if(option!=1) { iterate=false;}

		}	
	}
	public static void newAccount(HashMap<Long,CustomerDetails> bankAccount)
	{
		System.out.println("Enter the name");
		scan.nextLine();
		String userName=scan.nextLine();
		long initialAmount=0;
		System.out.println("Enter the pasword");
		iterate=true;
		String password="";
		while(iterate)
		{
			password=scan.next();
			if(checkPassword(password))	iterate=false;
			else System.out.println("Re enter password");
		}
		System.out.println("Enter the initial Amount");
		while(initialAmount<1000)
		{
			System.out.println("Note: The amount must be above 1000 Rupees.....");
			System.out.println();
			initialAmount=scan.nextLong();
		}
		System.out.println();
		System.out.println("Your Account Number is:"+accountNumber);
		bankAccount.put(accountNumber, new CustomerDetails(userName, password,
				initialAmount));
		System.out.println();
		Counts++;
		System.out.println("Your Account Created........");

	}
	public static void login(HashMap<Long,CustomerDetails> bankAccount,
			HashMap<Long,GiftCardDetails>  giftDetails)
	{
		iterate=true;
		Long accountNo=0l;
		String password="";
		while(iterate)
		{
			System.out.println("Enter the Correct Account Number here....");
			accountNo=scan.nextLong();
			while(bankAccount.containsKey(accountNo))
			{
				System.out.println("Enter the Correct Password Here....");
				password=scan.next();
				if(password.equals(bankAccount.get(accountNo).password))
				{
					iterate=false;
					break;
				}
			}
		}
		iterate=true;
		while(iterate)
		{
			
			System.out.println("Buy GiftCard                                  Press 1");
			System.out.println("View Account Balance                          Press 2");
			System.out.println("View GiftCard Balance                         Press 3");
			System.out.println("Check GiftCard is Active or Closed            Press 4");
			System.out.println("Purchase use Gift Card                        Press 5");
			System.out.println("Exit                                          Press 6");
			
			System.out.println("Enter the option");
			int option=scan.nextInt();
			if(option==6)break;
			switch(option)
			{
			case 1:buyGiftCard(accountNo,giftDetails,bankAccount);break;
			case 2:viewAccountBalance(bankAccount,accountNo);break;
			case 3:viewGiftCardBalance(giftDetails,accountNo);break;
			case 4:checkGiftcardActiveOrNot(giftDetails,accountNo);break;
			case 5:purchaseUseGiftCard(giftDetails,accountNo);

			}
		}
	}
	public static void buyGiftCard(Long accountNo,HashMap<Long,GiftCardDetails>  giftDetails,
			HashMap<Long,CustomerDetails> bankAccount)
	{
		System.out.println("------------------Gift Card Details------------------");
		System.out.println();
		System.out.println("CardName               Rate                Option ");
		System.out.println();
		System.out.println("Silver                 2000                 1     ");
		System.out.println("Gold                   2500                 2     ");
		System.out.println("Platinum               3000                 3     ");
		System.out.println();
		System.out.println("Choose the option");
		int option=scan.nextInt();
		option=option==1?2000:option==2?2500:3000;

		if(option>bankAccount.get(accountNo).accountBalance)
		{
			System.out.println("Insufficient money");
			return;
		}
		System.out.println("Your Gift Card Number is: "+giftCardNumber);
		System.out.println("Your Gift Card Pin is: "+pin);
		System.out.println();
		bankAccount.get(accountNo).accountBalance-=option;
		giftDetails.put(giftCardNumber,new GiftCardDetails(giftCardNumber++, accountNo, 
				pin++, option, "Activate") );
		System.out.println();
		System.out.println("Your Gift Card is Boughted");

	}
	public static void viewAccountBalance(HashMap<Long,CustomerDetails> bankAccount,long accountNo)
	{
		System.out.println();
		System.out.println("----------------Your Account balance--------------");
		System.out.println("Your Name:"+bankAccount.get(accountNo).name);
		System.out.println("Your Account Balance:"+bankAccount.get(accountNo).accountBalance);
		System.out.println();
	}
	public static void viewGiftCardBalance(HashMap<Long,GiftCardDetails>  giftDetails,long accountNo)
	{
		System.out.println();
		System.out.println("Enter the Id");
		long giftId=scan.nextInt();

		System.out.println("Enter the pin");
		long giftPin=scan.nextInt();
		if(giftDetails.get(giftId).AccountNo!=accountNo) {
			System.out.println("Enter the correct Gift Id");return;
		}
		if(giftPin==giftDetails.get(giftId).pin)
		{
			System.out.println("Your giftCard Balance is:"+giftDetails.get(giftId).giftCardBalance);
		}
		else System.out.println("Give Proper pin");
		System.out.println();
	}
	public static void checkGiftcardActiveOrNot(HashMap<Long,GiftCardDetails>  giftDetails,
			long accountNo)
	{
		System.out.println("Enter the gift Id");
		long giftId=scan.nextLong();
		System.out.println("Enter the gift Pin");
		long giftPin=scan.nextLong();

		if(giftDetails.get(giftId).AccountNo!=accountNo)
		{
			System.out.println("Enter the proper gift Id");
			return;
		}
		else  if(giftPin==giftDetails.get(giftId).pin)
		{
			System.out.println("Your Gift Card Status is :"+giftDetails.get(giftId).status);
		}
		else
			System.out.println("Enter the Correct Pin");
	}
	public static void purchaseUseGiftCard(HashMap<Long,GiftCardDetails>  giftDetails,
			long accountNo)
	{
		System.out.println("Enter the gift Card Number");
		long giftId=scan.nextLong();

		System.out.println("Enter the Pin");
		long giftPin=scan.nextLong();
		if(giftDetails.get(giftId).AccountNo!=accountNo)
		{
			System.out.println("Enter the Correct Gift Id");

		}
		if(giftPin!=giftDetails.get(giftId).pin)
		{
			System.out.println("Enter the Correct Pin");return;
		}
		System.out.println("1000         Press 1");
		System.out.println("1500         Press 2");
		System.out.println("2000         Press 3");
		System.out.println();
		System.out.println("Enter the option");
		int option=scan.nextInt();
		option=option==1?1000:option==2?1500:2000;
		if(option>giftDetails.get(giftId).giftCardBalance)
			System.out.println("Insufficient Balance");
		else
		{
			giftDetails.get(giftId).giftCardBalance-=option;
			System.out.println("Purchased SuccessFully");}

	}
	public static boolean checkPassword(String password)
	{
		return Pattern.matches("[^0-9][0-9a-zA-Z_/-/+]{6,}",password );
	}
}

class GiftCardDetails
{
	long giftId;
	long AccountNo;
	long pin;
	long giftCardBalance;
	String status="closed";

	public GiftCardDetails(long giftId,long AccountNo,long pin,
			long giftBalance,String status) {

		this.giftId=giftId;
		this.AccountNo=AccountNo;
		this.pin=pin;
		this.giftCardBalance+=giftBalance;
		this.status=status;
	}


}
class CustomerDetails
{
	String name;
	String password;
	long accountBalance;

	public CustomerDetails(String name,String passWord,long accountBalance) {

		this.name=name;
		this.password=passWord;
		this.accountBalance=accountBalance;
	}



}