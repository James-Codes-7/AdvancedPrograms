package mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Mail {

	static long mailId=1;
	static  boolean iterate=true;
	static String removed="removed";
	static Scanner scan=new Scanner(System.in);
	
	//it store email id and password
	static  HashMap<String,String> account=new HashMap<>();
	public static void main(String[] args) {

		//it store the list of the user sent the mails to other
		ArrayList<MailSyatem> userSentMails= new ArrayList<>();
		//it store the list of the user received from other
		ArrayList<MailSyatem> userReceivedMails= new ArrayList<>();
		//it is delete list of the user sent the mails to others
		ArrayList<MailSyatem> userDeletesentMails= new ArrayList<>();
		//it  is delete list of the user received from other
		ArrayList<MailSyatem> userDeletereceiveMails=new ArrayList<>();

		//it just sample store
		account.put("jack123@gmail.com", "Jack123");
		account.put("hentry@gmail.com", "hentry13");
		account.put("ruthrak3@gmail.com", "ruth123");
		
		//first page
		while(iterate)
		{
			System.out.println("New Account      press 1");
			System.out.println("Login            press 2");
			System.out.println();
			System.out.println("Enter the option");
			int option=scan.nextInt();
			switch(option)
			{
			//it is create new account
			case 1:newAccount(account);break;
			//it login only already user
			case 2:login(account,userSentMails,userReceivedMails,userDeletesentMails,
					userDeletereceiveMails);break;
			}
			System.out.println("Do u want continue press 1");
			int s=scan.nextInt();
			if(s==1)iterate=true;
			else iterate= false;
		}

	}
	public static void newAccount(HashMap<String,String> account)
	{
		 iterate=true;String userMailId="", userPassword="";
		System.out.println("Enter the Email Id");
		while(iterate)
		{
			userMailId=scan.next();
			if(!userMailId.endsWith("@gmail.com")||account.containsKey(userMailId))
			{
				System.out.println("Re enter mail");
			}
			else
				iterate=false;
		}
		iterate=true;
		while(iterate)
		{
			System.out.println("Enter the password");
			 userPassword=scan.next();
			if(userPassword.length()<6)System.out.println("Re enter password");
			else iterate=false;
		}
		account.putIfAbsent(userMailId, userPassword);
		System.out.println("Your Account is created");

	}
	public static void login(HashMap<String,String> account,ArrayList<MailSyatem> userSentMails
			,ArrayList<MailSyatem> userReceivedMails,ArrayList<MailSyatem> userDeletesentMails,
			ArrayList<MailSyatem> userDeletereceiveMails)
	{
		 iterate=true;
		String userMailId="";
		while(iterate)
		{
			System.out.println("Enter the user Name");
			userMailId=scan.next();
			while(account.containsKey(userMailId)&&iterate)
			{
				System.out.println("Enter the password");
				String userPassword=scan.next();
				if(account.get(userMailId).equals(userPassword))
					iterate=false;
			}
		}
		iterate=true;
		System.out.println("-------------------------------------------------------------------");
		while(iterate)
		{
			System.out.println("Compose                         press 1");//write and sent a mail
			System.out.println("list sentMails                  press 2");//view sent mail list
			System.out.println("List  received mails            press 3");//view receive mail list
			System.out.println("Delete send Mail                press 4");//delete the mail from sent list
			System.out.println("Delete receive Mail             press 5");//delete the mail from receive mail list
			System.out.println("Show sent delete mails          press 6");//show deleted sent mail list
			System.out.println("Show received delete mails      press 7");//show deleted received mail list

			System.out.println("Enter the option");
			int option=scan.nextInt();
			switch(option)
			{
			//sent the mail to another user
			case 1:compose(userSentMails,userReceivedMails,userMailId);
			break;
			//it is list of sent mails
			case 2:listSentmail(userSentMails,userMailId);
			break;
			//it is list of received mails
			case 3:receivedMails(userReceivedMails,userMailId);
			break;
			//it is delete the mail your wish in sent mails
			case 4:deleteSentMail(userSentMails,userDeletesentMails,userMailId);
			break;
			//it is delete mails your wish in receive mails
			case 5:deleteReceiveMail(userReceivedMails,userDeletereceiveMails,userMailId);
			break;
			//it is show delete mails from sent list
			case 6:showSentDeleteMails(userDeletesentMails,userMailId);
			break;
			//it is show delete mails from received list
			case 7:showReceiveDeleteMails(userDeletereceiveMails,userMailId);
			break;

			}
			System.out.println();
			System.out.println("Do you want continue press 1");
			int y=scan.nextInt();
			if(y==1) iterate=true;
			else iterate=false;
		}

	}
	public static void compose(ArrayList<MailSyatem> userSentMails,
			ArrayList<MailSyatem> userReceivedMails,String userName)
	{
		System.out.println("Enter the opposite userId");
		String oppositeUser=null;
		 iterate=true;
		while(iterate)
		{
			//if opposite user is wrong or not in user list it show re enter
			oppositeUser=scan.next();
			if(!account.containsKey(oppositeUser))
			{
				System.out.println("Re Enter opposite user id");
			}else iterate=false;
		}
		scan.nextLine();
		//you write  the message here
		System.out.println("Enter the subject");
		String subject=scan.nextLine();
		//it add the mail in sent list for one user who sent
		userSentMails.add(new MailSyatem(userName,mailId,userName,oppositeUser,subject,"send"));
		//it add the mail in receive list who receive the mail 
		userReceivedMails.add(new MailSyatem(oppositeUser,mailId,userName,oppositeUser,subject,
				"received"));
		mailId++;

	}
	public static  void listSentmail(ArrayList<MailSyatem> userSentMails,String userName)
	{
		System.out.println("User name:"+userName);
		System.out.println("Password:"+account.get(userName));
		//it show the list of sent mail without delete mails
		for(MailSyatem sentmails:userSentMails)
		{
			if(sentmails.userId.equals(userName)&&!sentmails.touser.equals(removed))
			{
				display(sentmails);
			}
		}
	}
	public static void receivedMails(ArrayList<MailSyatem> userReceivedMails,String userName)
	{
		System.out.println("User name:"+userName);
		System.out.println("Password:"+account.get(userName));
		System.out.println();
		//it show the receive mails without delete mails
		for(MailSyatem mails:userReceivedMails)
		{                                     //it mail delete i give remove in touser variable
			if(mails.userId.equals(userName)&&!mails.touser.equals(removed))
			{
				display(mails);
			}
		}
	}
	public static void deleteSentMail(ArrayList<MailSyatem> userSentMails,
			ArrayList<MailSyatem> userDeletesentMails,String userName)
	{
		System.out.println("Enter the message id");
		long messId=scan.nextLong();

		//it delete the sent mail use of message id
		for(MailSyatem mail:userSentMails)
		{
			if(mail.userId.equals(userName)&&mail.messageId==messId)
			{
				userDeletesentMails.add(new MailSyatem(mail.userId,mail.messageId,mail.From,
						mail.to,mail.subject,mail.touser));
				mail.touser=removed;
				System.out.println("Your sent mail is deleted");return;
			}

		}
		System.out.println("Cannot find this mail");


	}
	public static void deleteReceiveMail(ArrayList<MailSyatem> userReceivedMails,ArrayList<MailSyatem> userDeletereceiveMails,
			String userName)
	{
		System.out.println("Enter the message Id");
		long messId=scan.nextLong();
		//it delete the receive mail use of message id
		for(MailSyatem mail:userReceivedMails)
		{
			if(mail.userId.equals(userName)&&mail.messageId==messId)
			{
				userDeletereceiveMails.add(new MailSyatem(mail.userId,mail.messageId,mail.From,
						mail.to,mail.subject,mail.touser));
				mail.touser=removed;
				System.out.println("Your received mail is deleted");return;
			}
		}
		System.out.println();
		System.out.println("Cannot find this mail");
	}
	public static void showSentDeleteMails(ArrayList<MailSyatem> userDeletesentMails,String userName)
	{

		System.out.println("User name:"+userName);
		System.out.println("Password:"+account.get(userName));
		System.out.println();
		//it show user sent mail delete list
		for(MailSyatem mails:userDeletesentMails)
		{
			if(mails.userId.equals(userName))
			{
				display(mails);
			}
		}
	}
	public static void showReceiveDeleteMails(ArrayList<MailSyatem> userDeletereceiveMails,String userName)
	{
		System.out.println("User Id:"+userName);
		System.out.println("Pass word:"+account.get(userName));

		//it show the user receive mail delete list
		for(MailSyatem mails:userDeletereceiveMails)
		{
			if(mails.userId.equals(userName))
			{
				display(mails);
			}
		}
	}
	private static void display(MailSyatem mails)
	{
		System.out.println("Message Id:"+mails.messageId+", From:"+mails.From+", To:"+mails.to+" subject:"+mails.subject);
	}


}
class MailSyatem
{
	public String userId;
	public long messageId;
	public String From;
	public String to;
	public String subject;
	public String touser;

	public MailSyatem(String userID,long messageId,String from,String to,String subject,String toUser)
	{   
		this.userId=userID;
		this.messageId=messageId;
		this.From=from;
		this.to=to;
		this.subject=subject;
		this.touser=toUser;

	}
	{

	}




}
