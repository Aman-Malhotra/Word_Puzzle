package ai_programs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.omg.CORBA.PUBLIC_MEMBER;

class word_puzzles extends JFrame implements ActionListener
{
	Scanner s= new Scanner(System.in);
	int row,col;
	String a[]= new String[12*(10-1)];
	String aa[]= new String[8];
	
	JPanel p; 
	JButton [][]b ;
	JButton add,find,clear ;
	JTextField t1;
	JLabel l1 ,l2;
	JList list;
	DefaultListModel dlm;
	JScrollPane scroll;
	
	Connection con ;
	PreparedStatement pst;
	
	word_puzzles()
	{
//		System.out.println("Enter Rows::" );
//		row = s.nextInt();
//		System.out.println("Enter Columns::" );
//		col = s.nextInt();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary","root","bce");
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		p = new JPanel();
		p.setLayout(null);
		p.setBounds(0, 0, 1370, 730);
		p.setBackground(Color.decode("#42A5F5"));
		add(p);
		
		b = new JButton[10][10];
		int x=250,y=130;
		
		Random ran = new Random();
		ran.setSeed(Calendar.getInstance().getTimeInMillis());
		for(int i = 0 ; i < 10 ; i ++)
		{
			for(int j = 0 ; j < 10 ; j++)
			{
				char ch = ' ';
				int no = ran.nextInt(26)+97;
				ch = (char)no;
				b[i][j]= new JButton();
				b[i][j].setBackground(Color.WHITE);
				b[i][j].setBounds(x+(50*j), y, 50, 50);
				b[i][j].setText( String.valueOf(ch) );
				b[i][j].setFont(new Font("Arial", Font.BOLD, 15));
//				System.out.print((char)97+"  "+(char)122);
				p.add(b[i][j]);
			}
			y=y+50;
			x=250;
			System.out.println();
		}
//		b.setBounds(30,30,50,50);
//		p.add(b);
		
		l1 = new JLabel("Add Word");
		l1.setBounds(400,60,100,40);
		l1.setFont(new Font("Arial", Font.BOLD, 20));
		l2 = new JLabel("Words Found");
		l2.setBounds(1030,110,200,40);
		l2.setFont(new Font("Arial", Font.BOLD, 20));
		p.add(l1);p.add(l2);
		
		t1 = new JTextField();
		t1.setBackground(Color.WHITE);
		t1.setBounds(600, 60, 300, 40);
		p.add(t1);
		
		add = new JButton("ADD");
		add.setBounds(1000, 60, 100, 40);
		add.setBackground(Color.WHITE);
		add.setFont(new Font("Arial",Font.BOLD,15) );
		find = new JButton("FIND WORDS");
		find.setBounds(20, 570, 200, 40);
		find.setBackground(Color.WHITE);
		find.setFont(new Font("Arial",Font.BOLD,15) );
		clear = new JButton("Clear List");
		clear.setBounds(800, 570, 120, 40);
		clear.setBackground(Color.WHITE);
		clear.setFont(new Font("Arial",Font.BOLD,15) );
		p.add(add);p.add(find);p.add(clear);
		
		dlm = new DefaultListModel();
		list = new JList(dlm);
		list.setBounds(1000, 150, 200, 530);
		list.setBackground(Color.WHITE);
		scroll = new JScrollPane(list);
		scroll.setBounds(1000,150,200,530);
//		p.add(list);
		p.add(scroll);
				
		setLayout(null);
		setBounds(0,0,1370,730);
		setVisible(true);
		
//-----------------------------------ADDing Words to the Database ----------------------------------------
//		String sss="";
//		for(int i = 0 ; true ; i ++)
//		{
//			sss = s.nextLine();
//			if(sss.equals("aaaaa") )
//			{
//				break;
//			}
//			try 
//			{
//				pst = con.prepareStatement("insert into word_list_copy value(?)");
//				pst.setString(1, sss);
//				pst.executeUpdate();
//			}
//			catch (SQLException e1) 
//			{
//				e1.printStackTrace();
//			}
//						
//		}

		
//---------------------------------------Action Listener---------------------------------------------------
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				add_word();
				
			}
		});
		
		find.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				find_word(a);
			}
		});
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dlm.clear();
			}
		});
		
		for(int i = 0;i<10;i++)
			for(int j = 0; j<10;j++)
				b[i][j].addActionListener(this);
		
//			public void actionPerformed(ActionEvent e) 
//			{
//				b[0][0].addActionListener(this);
//			}
//		b[0][0].addActionListener()
				
//-----------------------------------Calling form_word() ------------------------------------------
		form_word();
		
	}
//---------------------------------adding action listener---------------to Buttons Clicked----------	
	public void actionPerformed(ActionEvent e)
	{
		for(int i = 0;i<10;i++)
			for(int j = 0 ; j < 10;j++)
				if(e.getSource()==b[i][j])
		{
			click(i, j);
//			System.out.println(e.getSource());
//			find_word(aa);
		}
	}
	
//-----------------------------------------For Adding words "FUNCTION" ------------------------------------
	
	void add_word()
	{
		try 
		{
			if(t1.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Enter the word....");
			}
			else
			{
				pst = con.prepareStatement("insert into word_list values(?)");
				pst.setString(1, t1.getText());
				int yes = JOptionPane.showConfirmDialog(null, "Are Sure you want to add \""+t1.getText()+"\" ?");
				if(yes==0)
				{
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Word added");
				}
			}
		}
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "\t**Word Already Exist**");
		}
	}
//----------------------------------------Forming array of words -------------Function---------------------------
	void form_word()
	{
		String lr = "";
		String td = "";
		String rl = "";
		String dt = "";
		String daigonal_left = "";
		String daigonal_right = "";
		String rev_daigonal_left = "";
		String rev_daigonal_right = "";
		String dai_dt_l = "";
		String r_dai_dt_l = "";
		String dai_dt_r = "";
		String r_dai_dt_r = "";
		
		
		int index = 0;		
		for(int i = 0 ; i < 10 ; i++)
		{
			int lastindex = 10-1;
			for(int j = 0 ; j < 10; j++)
			{
				lr = lr.concat( b[i][j].getText() );			//	left to right
				rl = rl.concat( b[i][lastindex-j].getText() );	//	 right to left 
				td = td.concat( b[j][i].getText() );			//	top to down
				dt = dt.concat( b[lastindex-j][i].getText() );	//	down to top
				
			}
			a[index++] = lr;
			a[index++] = rl;
			a[index++] = td;
			a[index++] = dt;
//			a[index++] = td.substring(4, 0);	// Throw Exception
			
			lr=rl=td=dt="";
		}
		for(int i = 0 ;i<10-1;i++ )
		{
			int lastindex = 10-1;
			for(int j=0 ; j<10-i ;j++)
			{
				daigonal_left = daigonal_left.concat(b[j+i][j].getText());
				rev_daigonal_left = rev_daigonal_left.concat(b[lastindex-j][lastindex-j-i].getText());
				
				dai_dt_l = dai_dt_l.concat(b[lastindex-j-i][j].getText());
				r_dai_dt_l = r_dai_dt_l.concat(b[j][lastindex-j-i].getText());
				if(i==0)
					continue;
				daigonal_right = daigonal_right.concat(b[j][j+i].getText());
				rev_daigonal_right = rev_daigonal_right.concat(b[lastindex-j-i][lastindex-j].getText());
				
				dai_dt_r = dai_dt_r.concat(b[lastindex-j][j+i].getText());
				r_dai_dt_r = r_dai_dt_r.concat(b[j+i][lastindex-j].getText());
			}
			a[index++] = daigonal_left;
			a[index++] = rev_daigonal_left;
			a[index++] = dai_dt_l;
			a[index++] = r_dai_dt_l;
			if(i!=0)
			{
				a[index++] = daigonal_right;
				a[index++] = rev_daigonal_right;
				a[index++] = dai_dt_r;
				a[index++] = r_dai_dt_r;
			}
			daigonal_left = rev_daigonal_left = daigonal_right = rev_daigonal_right = dai_dt_l = r_dai_dt_l = "";
			dai_dt_r = r_dai_dt_r ="";
		}
		
//		for(int i = 0 ; i < 50 ; i++)
//		{
//			System.out.println(a[i]);	
//		}	

	}
//----------------------------------------Finding word ------------------------------------------------------
	void find_word(String o[])
	{
		
		try
		{
			pst = con.prepareStatement("Select * from word_list_copy");
			ResultSet rs = pst.executeQuery();
			
			int total = 0,vowel=0,conso=0;
			while(rs.next())
			{
				int count=0;
//				for(int i = 0 ; i < 12*(10-1) ; i++)
				for(int i = 0 ; i < o.length ; i++)
				{
					if(o[i].contains(rs.getString(1)) )
					{
						count++;
						dlm.addElement(rs.getString(1)+" --> "+count+" time occured");
						if(rs.getString(1).startsWith("a")||rs.getString(1).startsWith("e")||rs.getString(1).startsWith("i")
								||rs.getString(1).startsWith("o")||rs.getString(1).startsWith("u"))
						{
							vowel++;
						}
						else
						{
							conso++;
						}
//						System.out.println(rs.getString(1));
					}	
				}	
				total=total+count;
			}	
			dlm.addElement("Total Words found:: "+total);
			dlm.addElement("Total Vowels found:: "+vowel);
			dlm.addElement("Total Consonants found:: "+conso);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
//------------------------------------for click----------------------------
	void click(int r,int c)
	{
		String lr = "";
		String rl = "";
		String td = "";
		String dt = "";
		String d_t = "";
		String d_b = "";
		String r_d_t = "";
		String r_d_b = "";
		int index=0;
//		for(int i = 0 ; i < 10-1; i++)
		{
			int lastindex= 9;
			for(int j = 0 ; j < 10 ; j++)
			{
				lr = lr.concat(b[r][j].getText());
				rl = rl.concat(b[r][lastindex-j].getText());
				td = td.concat(b[j][c].getText());
				dt = dt.concat(b[lastindex-j][c].getText());
			}
			
			aa[index++]=lr;
			aa[index++]=rl;
			aa[index++]=td;
			aa[index++]=dt;
			lr=rl=td=dt="";
		}
		
		int lastindex = 10-1;
		int diff = r-c;
		if(diff>=0)
		{
			for(int i = 0 ; i < 10-diff ; i++)
			{
				d_t = d_t.concat(b[diff+i][i].getText());
				r_d_t = r_d_t.concat(b[lastindex-i][lastindex-i-diff].getText());
				d_b = d_b.concat(b[lastindex-diff-i][i].getText());
				r_d_b = r_d_b.concat(b[i][lastindex-diff-i].getText());
			}
		}	
		else
		{
			diff=-diff;
			for(int i = 0 ; i < 10-diff ; i++)
			{
				d_t = d_t.concat(b[i][diff+i].getText());
				r_d_t = r_d_t.concat(b[lastindex-diff-i][lastindex-i].getText());
				d_b = d_b.concat(b[lastindex-i][i].getText());
				r_d_b = r_d_b.concat(b[diff+i][lastindex-i].getText());
			}
		}
		
		aa[index++] =d_t;
		aa[index++] =r_d_t;
		aa[index++] =d_b;
		aa[index++] =r_d_b;
		
		for(int i = 0 ; i < 8 ; i++)
		{
			System.out.println(aa[i]);
		}
		
		try
		{
			pst = con.prepareStatement("Select * from word_list_copy");
			ResultSet rs = pst.executeQuery();
			
			int total = 0,vowel=0,conso=0;
			diff = r-c;
			while(rs.next())
			{
				int count=0;
				for(int i = 0 ; i < aa.length ; i++)
				{
//					System.out.println(aa[i].charAt(r));
					if(   ( aa[i].contains(rs.getString(1)) ) && rs.getString(1).contains(b[r][c].getText()) )
//							&&( (b[r][c].getText().equals(( aa[i].charAt(r) ) )  )||
//								(b[r][c].getText().contentEquals( aa[i].substring(10-r, 10-r) ) )||
//								(b[r][c].getText().contentEquals( aa[i].substring(c, c) )  )||
//								(b[r][c].getText().contentEquals( aa[i].substring(10-c,10-c) ) )	//||
////								
////								(b[r][c].getText() == a[i].substring(diff+r, diff+r) )//||
//////								(b[r][c].getText() == a[i].substring(10-diffr, 10-diffr) )||
//////								(b[r][c].getText() == a[i].substring(r, r) )||
//////								(b[r][c].getText() == a[i].substring(r, r) )||
								
//						)
					{
						count++;
						dlm.addElement(rs.getString(1)+" --> "+count+" time occured");
						if(rs.getString(1).startsWith("a")||rs.getString(1).startsWith("e")||rs.getString(1).startsWith("i")
								||rs.getString(1).startsWith("o")||rs.getString(1).startsWith("u"))
						{
							vowel++;
						}
						else
						{
							conso++;
						}
//						break;
					}	
				}	
				total=total+count;
			}	
			dlm.addElement("Total Words found:: "+total);
			dlm.addElement("Total Vowels found:: "+vowel);
			dlm.addElement("Total Consonants found:: "+conso);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
};

public class Word_Puzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		new word_puzzles();
	}
}
