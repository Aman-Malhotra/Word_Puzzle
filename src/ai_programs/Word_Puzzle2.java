

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

class word_puzzles1 extends JFrame implements ActionListener
{
	Scanner s= new Scanner(System.in);
	int row,col;
	static int count =0;
	String a[]= new String[12*(10-1)];
	String aa[]= new String[8];
	
	JPanel p; 
	JButton [][]b ;
	JButton add,find,clear,find2,find3,replace ;
	JTextField t1;
	JLabel l1 ,l2;
	JList list;
	DefaultListModel dlm;
	JScrollPane scroll;
	
	Connection con ;
	PreparedStatement pst;
	ResultSet rs;
	
	word_puzzles1()
	{
//		System.out.println("Enter Rows::" );
//		row = s.nextInt();
//		System.out.println("Enter Columns::" );
//		col = s.nextInt();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_pro","root","aman");
			
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

				p.add(b[i][j]);
			}
			y=y+50;
			x=250;
			System.out.println();
		}
		
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
		find2 = new JButton("FIND WORDS 2");
		find2.setBounds(20, 470, 200, 40);
		find2.setBackground(Color.WHITE);
		find2.setFont(new Font("Arial",Font.BOLD,15) );
		find3 = new JButton("FIND WORDS 3");
		find3.setBounds(20, 400, 200, 40);
		find3.setBackground(Color.WHITE);
		find3.setFont(new Font("Arial",Font.BOLD,15) );
		replace = new JButton("Replace Word");
		replace.setBounds(20, 400, 200, 40);
		replace.setBackground(Color.WHITE);
		replace.setFont(new Font("Arial",Font.BOLD,15) );
		clear = new JButton("Clear List");
		clear.setBounds(800, 570, 120, 40);
		clear.setBackground(Color.WHITE);
		clear.setFont(new Font("Arial",Font.BOLD,15) );
		p.add(add);p.add(find);p.add(clear);p.add(find2);p.add(find3);p.add(replace);
		
		dlm = new DefaultListModel();
		list = new JList(dlm);
		list.setBounds(1000, 150, 200, 530);
		list.setBackground(Color.WHITE);
		scroll = new JScrollPane(list);
		scroll.setBounds(1000,150,250,530);
                
                p.add(scroll);
//		list.setVisible(false);
				
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
//				list.setVisible(true);
			}
		});
	
		find2.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				find_word(a);
				form_word2();
				list.setVisible(true);
			}
		});
		
		find3.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i = 0 ; i < 10 ; i++)
					for(int  j = 0 ; j <10 ; j++)
					{
//						button_fun(i, j);
						l_r(i,j);
						r_l(i,j);
						t_d(i,j);
						d_t(i,j);
						if((i-j)>=0)
						{	
							dai_td_left(i, j);
							rev_dai_td_left(i, j);
						}
						else
						{	
							dai_td_right(i, j);
							rev_dai_td_right(i, j);
						}
						if((i+j)<=9)
						{
							dai_dt_left(i, j);
							rev_dai_dt_left(i, j);
						}
						else
						{	
							dai_dt_right(i, j);
							rev_dai_dt_right(i, j);
						}
					}
			}
		});
		
		replace.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dlm.clear();
				count=0;
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
				
//-----------------------------------Loading Result set--------------------------------------------
		try
		{
			pst = con.prepareStatement("Select * from word_list_copy");
			rs = pst.executeQuery();
		}
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
//-----------------------------------Calling form_word() ------------------------------------------
		form_word();
		
	}	// Constructor Closed
	
//---------------------------------adding action listener---------------to Buttons Clicked----------	
	public void actionPerformed(ActionEvent e)
	{
		for(int r = 0;r<10;r++)
			for(int c = 0 ; c < 10;c++)
				if(e.getSource()==b[r][c])
		{
			int res = JOptionPane.showConfirmDialog(null, "Want To Replace ?");		
			if(res==0)
			{
				replace(r, c);
				System.out.println(res);
			}
			int index = 0;
			for(int j = 0 ; j < c ; j++ )		// left to right
			{	
				l_r(r, j);
//				find_word2(str, i, j);
				if(!(dlm.isEmpty()) )
				{	
					for(int l = index ; l < dlm.size() ; l++)
					{
						System.out.println(dlm.getElementAt(dlm.size()-1));
//						System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
						if(	! ( (((String)dlm.get(l)).substring(c-j, c-j+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
						{
							dlm.removeElementAt(l);
//							dlm.removeElement(dlm.lastElement());
						}						
						System.out.println(dlm.getElementAt(dlm.size()-1));
					}
					index = dlm.size()-1;
				}
			}
			for(int j = 10-1 ; j > c ; j-- )	// right to left
			{
				r_l(r,j);
				if(!(dlm.isEmpty()) )
				{	
					for(int l = index ; l < dlm.size() ; l++)
					{
//						System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
						if(	! ( (((String)dlm.get(l)).substring(j-c, j-c+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
						{
							dlm.removeElementAt(dlm.size()-1);
//							dlm.removeElement(dlm.lastElement());
						}
					}
					index = dlm.size()-1;
				}
			}
			for(int i = 0 ; i < r ; i++ )		// top to down
			{
				t_d(i,c);
				if(!(dlm.isEmpty()) )
				{	
					for(int l = index ; l < dlm.size() ; l++)
					{
//						System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
						if(	! ( (((String)dlm.get(l)).substring(r-i, r-i+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
						{
							dlm.removeElementAt(dlm.size()-1);
//							dlm.removeElement(dlm.lastElement());
						}
//						index = dlm.size()-1;
					}
					index = dlm.size()-1;
				}
			}
			for(int i = 10-1 ; i > r ; i-- )	// down to top
			{
				d_t(i,c);
				if(!(dlm.isEmpty()) )
				{	
					for(int l = index ; l < dlm.size() ; l++)
					{
//						System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
						if(	! ( (((String)dlm.lastElement()).substring(i-r, i-r+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
						{
							dlm.removeElementAt(dlm.size()-1);
//							dlm.removeElement(dlm.lastElement());
						}
//						index = dlm.size()-1;
					}
					index = dlm.size()-1;
				}
			}
//---------------------------diagonals word's comparison (index of letter in word)------------------ 		
			int col;
			if((r-c)>=0)
			{
				col = 0;
				for(int i = r-c ; i < r ; i++)		// daigonal Top to Down left
				{
					dai_td_left(i, col);
					col++;
					if(!(dlm.isEmpty()) )
					{	
						for(int l = index ; l < dlm.size() ; l++)
						{
//							System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
							if(	! ( (((String)dlm.get(l)).substring(r-i, r-i+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
							{
								dlm.removeElementAt(l);
//								dlm.removeElement(dlm.lastElement());
							}
//							index = dlm.size()-1;
						}
						index = dlm.size()-1;
					}
				}
				col = 10-1-(r-c);
				for(int i = 10-1 ; i > r ; i-- )	// Rev_daigonal top to down left
				{
					rev_dai_td_left(i, col);
					col--;
					if(!(dlm.isEmpty()) )
					{	
						for(int l = index ; l < dlm.size() ; l++)
						{
//							System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
							if(	! ( (((String)dlm.get(l)).substring(i-r, i-r+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
							{
								dlm.removeElementAt(l);
//								dlm.removeElement(dlm.lastElement());
							}
//							index = dlm.size()-1;
						}
						index = dlm.size()-1;
					}
				}
			}	
			else
			{
				col = c-r;
				for(int i = 0 ; i < r ; i++)
				{
					dai_td_right(i, col);
					col++;
					if(!(dlm.isEmpty()) )
					{	
						for(int l = index ; l < dlm.size() ; l++)
						{
//							System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
							if(	! ( (((String)dlm.get(l)).substring(r-i, r-i+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
							{
								dlm.removeElementAt(l);
//								dlm.removeElement(dlm.lastElement());
							}
//							index = dlm.size()-1;
						}
						index = dlm.size()-1;
					}
				}
				col=10-1;
				for(int i = 10-1-(c-r) ; i > r ; i--)
				{
					rev_dai_td_right(i, col);
					col--;
					if(!(dlm.isEmpty()) )
					{	
						for(int l = index ; l < dlm.size() ; l++)
						{
//							System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
							if(	! ( (((String)dlm.get(l)).substring(i-r, i-r+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
							{
								dlm.removeElementAt(l);
//								dlm.removeElement(dlm.lastElement());
							}
//							index = dlm.size()-1;
						}
						index = dlm.size()-1;
					}
				}
			}
			
			if((r+c)<=9)
			{
				col = 0;
				for(int i = /*10-1-*/(r+c) ; i > r ; i--)
				{
					dai_dt_left(i, col);
					col++;
					if(!(dlm.isEmpty()) )
					{	
						for(int l = index ; l < dlm.size() ; l++)
						{
//							System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
							if(	! ( (((String)dlm.get(l)).substring(i-r, i-r+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
							{
								dlm.removeElementAt(l);
//								dlm.removeElement(dlm.lastElement());
							}
//							index = dlm.size()-1;
						}
						index = dlm.size()-1;
					}
				}
				col=r+c;
				for(int i = 0 ; i < r ; i++)
				{
					rev_dai_dt_left(i, col);
					col--;
					if(!(dlm.isEmpty()) )
					{	
						for(int l = index ; l < dlm.size() ; l++)
						{
//							System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
							if(	! ( (((String)dlm.get(l)).substring(r-i, r-i+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
							{
								dlm.removeElementAt(l);
//								dlm.removeElement(dlm.lastElement());
							}
//							index = dlm.size()-1;
						}
						index = dlm.size()-1;
					}
				}
			}
			else
			{
				col = r+c-9;
				for(int i = 10-1 /*0 */; i>r ; i--)
				{
					dai_dt_right(i, col);
					col++;
					if(!(dlm.isEmpty()) )
					{	
						for(int l = index ; l < dlm.size() ; l++)
						{
//							System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
							if(	! ( (((String)dlm.get(l)).substring(i-r, i-r+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
							{
								dlm.removeElementAt(l);
//								dlm.removeElement(dlm.lastElement());
							}
//							index = dlm.size()-1;
						}
						index = dlm.size()-1;
					}
				}
				col = 10-1;
				for(int i = r+c-9 ; i < r ; i++ )
				{
					rev_dai_dt_right(i, col);
					col--;
					if(!(dlm.isEmpty()) )
					{	
						for(int l = index ; l < dlm.size() ; l++)
						{
//							System.out.println( (((String)dlm.lastElement()).substring(c-j, c-j+1)/*charAt(c-j)*/) );
							if(	! ( (((String)dlm.get(l)).substring(r-i, r-i+1)/*charAt(c-j)*/).equals(b[r][c].getText()))  )// == (char)(Integer.parseInt((b[r][c].getText()))) )  )
							{
								dlm.removeElementAt(l);
//								dlm.removeElement(dlm.lastElement());
							}
//							index = dlm.size()-1;
						}
						index = dlm.size()-1;
					}
				}
			}
			
			
			button_fun(r, c);
//			click(i, j);
//			System.out.println(e.getSource());
//			find_word(aa);
		}
	}
//===============================================Button_function==========================================
	/* Instead of loops we had used functions for each direction like left to right , right to left , top to down , down to top 
	   diagonal( left top to down , left down to top )
	*/
	void l_r(int r, int c)
	{
		String lr = "";
		for(int j = c ; j < 10 ; j++)	//==========left to right
		{
				lr = lr.concat(b[r][j].getText());
				if(j!=c)
				{
					find_word2(lr, r, c);
				}
		}
		
	}
	
	void r_l(int r, int c)
	{
		String rl = "";
		for(int j = c ; j >= 0 ; j--)	//==========right to left
		{
			rl = rl.concat(b[r][j].getText());
			if(j!=c)
			{
				find_word2(rl, r, c);
			}
		}
	}

	void t_d(int r, int c)
	{
		String td = "";
		for(int i = r ; i < 10 ; i++)	//==========top to bottom
		{
			td = td.concat(b[i][c].getText());
			
			if(i!=r)
			{
				find_word2(td, r, c);
			}
		}
	}
	
	void d_t(int r, int c)
	{
		String dt = "";
		for(int i = r ; i >= 0 ; i--)	//===========bottom to top
		{
			dt = dt.concat(b[i][c].getText());
			
			if(i!=r)
			{
				find_word2(dt, r, c);
			}
		}
	}
	
	void dai_td_left(int r, int c)
	{
		String daigonal_left = "";
		int col = c;
		if((r-c)>=0)
		{
			for(int i = r ;i<10 ; i++ )
			{
				daigonal_left = daigonal_left.concat(b[i][col].getText());
				col++;
				if(i!=r)
				{
					find_word2(daigonal_left, r, c);
				}
			}
		}
	}
	void rev_dai_td_left(int r, int c)
	{
		String rev_daigonal_left = "";
		int row = r;
		if((r-c)>=0)
		{
			for(int j = c ;j>=0 ; j-- )
			{
				rev_daigonal_left = rev_daigonal_left.concat(b[row][j].getText());
				row--;
				if(j!=c)
				{
					find_word2(rev_daigonal_left, r, c);
				}
			}
		}
	}
	void dai_td_right(int r, int c)
	{
		String daigonal_right = "";
		int row = r ;
		if((c-r)>0)
		{
			for(int j = c ;j<10 ; j++ )
			{
				daigonal_right = daigonal_right.concat(b[row][j].getText());
				row++;
				if(j!=c)
				{
					find_word2(daigonal_right, r, c);
				}
			}
		}
		
	}
	void rev_dai_td_right(int r, int c)
	{
		String rev_daigonal_right = "";
		int col = c ;
		if((c-r)>0)
		{
			for(int i = r ;i >= 0 /*&& col>=0*/ ; i-- )
			{
				rev_daigonal_right = rev_daigonal_right.concat(b[i][col].getText());
				col--;
				if(i!=r)
				{
					find_word2(rev_daigonal_right, r, c);
				}
			}
		}
	}
	void dai_dt_left(int r, int c)
	{
		String dai_dt_l = "";
		int col = c ;
		if((r+c)<=9)
		{	for(int i = r ; i >= 0 ; i--)
			{
				dai_dt_l = dai_dt_l.concat(b[i][col].getText());
				col++;
				if(i!=r)
				{
					find_word2(dai_dt_l, r, c);
				}
			}
		}
	}
	void rev_dai_dt_left(int r, int c)
	{
		String r_dai_dt_l = "";
		int row = r;
		if((r+c)<=9)
		{
			for(int j = c  ; j >= 0 ; j--)
			{
				r_dai_dt_l = r_dai_dt_l.concat(b[row][j].getText());
				row++;
				if(j!=c)
				{
					find_word2(r_dai_dt_l, r, c);
				}
			}
		}
	}
	void dai_dt_right(int r, int c)
	{
		String dai_dt_r = "";
		int col = c ;
		if((r+c)>9)
		{
			for(int i = r ; i < 10 ; i++)
			{
				dai_dt_r = dai_dt_r.concat(b[i][col].getText());
				col--;
				if(i!=r)
				{
					find_word2(dai_dt_r, r, c);
				}
			}
		}
	}
	void rev_dai_dt_right(int r, int c)
	{
		String r_dai_dt_r = "";
		int row = r;
		if((r+c)>9)
		{
			for(int j = c  ; j < 10 ; j++)
			{
				r_dai_dt_r = r_dai_dt_r.concat(b[row][j].getText());
				row--;
				if(j!=c)
				{
					find_word2(r_dai_dt_r, r, c);
				}
			}
		}
	}
	
	void button_fun(int r,int c)	// this function is similar to " form_word2 " but difference is that in this we have used
	{								// separate loops for left to right , right to left , top to down , down to top , 
		String lr = "";				// diagonals( left top to down....and so on....) word's concatenation and calling function 
		String td = "";				// " find_word2 " directly
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
			
		int lastindex = 10-1;
			
		for(int j = c ; j < 10 ; j++)	//==========left to right
		{
				lr = lr.concat(b[r][j].getText());
				if(j!=c)
				{
					find_word2(lr, r, c);
				}
		}
		for(int j = c ; j >= 0 ; j--)	//==========right to left
		{
			rl = rl.concat(b[r][j].getText());
			if(j!=c)
			{
				find_word2(rl, r, c);
			}
		}
		for(int i = r ; i < 10 ; i++)	//==========top to bottom
		{
			td = td.concat(b[i][c].getText());
			
			if(i!=r)
			{
				find_word2(td, r, c);
			}
		}
		for(int i = r ; i >= 0 ; i--)	//===========bottom to top
		{
			dt = dt.concat(b[i][c].getText());
			
			if(i!=r)
			{
				find_word2(dt, r, c);
			}
		}
//========================== Daigonal top to down==================================		
		int diff = r-c;
		int col = c;
		int row = r;
		if((r-c)>=0)
		{
			for(int i = r ;i<10 ; i++ )
			{
				daigonal_left = daigonal_left.concat(b[i][col].getText());
				col++;
				if(i!=r)
				{
					find_word2(daigonal_left, r, c);
				}
			}
			col = c;
			for(int j = c ;j>=0 ; j-- )
			{
				rev_daigonal_left = rev_daigonal_left.concat(b[row][j].getText());
				row--;
				if(j!=c)
				{
					find_word2(rev_daigonal_left, r, c);
				}
			}
			row=r;
		}
		else
		{
			for(int j = c ;j<10 ; j++ )
			{
				daigonal_right = daigonal_right.concat(b[row][j].getText());
				row++;
				if(j!=c)
				{
					find_word2(daigonal_right, r, c);
				}
			}
			row = r;
			for(int i = r ;i >= 0 ; i-- )
			{
				rev_daigonal_right = rev_daigonal_right.concat(b[i][col].getText());
				col--;
				if(i!=r)
				{
					find_word2(rev_daigonal_right, r, c);
				}
			}	
			col = c;
		}
//=============================down to top and left to right==========================		
		if((r+c)<=9)
		{	for(int i = r ;i>=0;i--)
			{
				dai_dt_l = dai_dt_l.concat(b[i][col].getText());
				col++;
				if(i!=r)
				{
					find_word2(dai_dt_l, r, c);
				}
			}
			col = c;
			for(int j = c  ; j >= 0 ; j--)
			{
				r_dai_dt_l = r_dai_dt_l.concat(b[row][j].getText());
				row++;
				if(j!=c)
				{
					find_word2(r_dai_dt_l, r, c);
				}
			}
		}
		else
		{
			for(int i = r ; i < 10 ; i++)
			{
				dai_dt_r = dai_dt_r.concat(b[i][col].getText());
				col--;
				if(i!=r)
				{
					find_word2(dai_dt_r, r, c);
				}
			}
			col = c;
			for(int j = c  ; j < 10 ; j++)
			{
				r_dai_dt_r = r_dai_dt_r.concat(b[row][j].getText());
				row--;
				if(j!=c)
				{
					find_word2(r_dai_dt_r, r, c);
				}
			}
			row = r;
		}
				
	}
	
//======================================= REPLACE Function================================================
	
	void replace(int r , int c)
	{
		
		Random ran = new Random();
		ran.setSeed(Calendar.getInstance().getTimeInMillis());
		b[r][c].setText( String.valueOf((char)(ran.nextInt(26)+97)) );
		
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
	void form_word()		//------- to store all lines( row wise , column wise , diagonals ) of matrix in array  
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
	
//-------------------------------FUNCTION form_word2-----------------------------------------------	
	void form_word2()		// in this we don't store word and directly calling function "find_word2 " after concatenation 
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
//		boolean bool=false;
		
	for(int k = 0 ; k<9 ; k++)
	{
		int lastindex = 10-1;
		for(int i = 0 ; i < 10 ; i++)
		{			
			for(int j = k ; j < 10; j++)
			{
//				bool=false;
				lr = lr.concat( b[i][j].getText() );			//	left to right
				
				
				rl = rl.concat( b[i][lastindex-j/*-k*/].getText() );	//	 right to left 
				
				td = td.concat( b[j][i].getText() );			//	top to down
				
				dt = dt.concat( b[lastindex-j/*-k*/][i].getText() );	//	down to top
				
				if(j!=k)
				{	
//					System.out.println(rl);
					find_word2(lr,i,k);
					find_word2(rl,i,lastindex-k/*-j-k*/);
					find_word2(td,k,i);
					find_word2(dt,lastindex-k/*-j-k*/,i);
				}
			}
			lr=rl=td=dt="";
		}
	}
	
	for(int k = 0 ; k < 9 ; k++)
	{
		for(int i = 0 ;i<10-1;i++ )
		{
			int lastindex = 10-1;
			for(int j=k ; j<10-i ;j++)
			{
				daigonal_left = daigonal_left.concat(b[j+i][j].getText());
				rev_daigonal_left = rev_daigonal_left.concat(b[lastindex-j][lastindex-j-i].getText());
				dai_dt_l = dai_dt_l.concat(b[lastindex-i-j][j].getText());
				r_dai_dt_l = r_dai_dt_l.concat(b[j][lastindex-j-i].getText());

				if(j!=k)
				{
					find_word2(daigonal_left,k+i,k);
					find_word2(rev_daigonal_left,lastindex-k,lastindex-k-i);
					find_word2(dai_dt_l,lastindex-i-k,k);
					find_word2(r_dai_dt_l,k,lastindex-i-k);
				}
				if(i==0)
					continue;
				
				daigonal_right = daigonal_right.concat(b[j/*+k*/][j+i/*+k*/].getText());
				
				rev_daigonal_right = rev_daigonal_right.concat(b[lastindex-j-i/*-k*/][lastindex-j/*-k*/].getText());
				
				dai_dt_r = dai_dt_r.concat(b[lastindex-j/*-k*/][j+i/*+k*/].getText());
				
				r_dai_dt_r = r_dai_dt_r.concat(b[j+i/*+k*/][lastindex-j/*-k*/].getText());
				
				if(j!=k)
				{	
					find_word2(daigonal_right,/*j*/ k,/*j+*/i+k);
					find_word2(rev_daigonal_right,lastindex-i-k,lastindex-k);
					find_word2(dai_dt_r,lastindex-k,/*j+*/i+k);
					find_word2(r_dai_dt_r,/*j+*/i+k,lastindex-k);
				}
			}
			daigonal_left = rev_daigonal_left = daigonal_right = rev_daigonal_right = dai_dt_l = r_dai_dt_l = "";
			dai_dt_r = r_dai_dt_r ="";
		}
	}

	
	}
//----------------------------------------Finding word ------------------------------------------------------
	void find_word(String o[]) // comparing each word in dictionary to all elements in array i.e. matrix line 
	{						   // using "contains function" .
		
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
					if(a[i].contains(rs.getString(1)) )
					{
						count++;
						int indx = a[i].indexOf(rs.getString(1));
						dlm.addElement(rs.getString(1)+" --> "+count+" time occured at index : "+ indx );
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
//----------------------NEW FUNCTION TO FIND   ""****Words****""
	
	void find_word2(String str,int i,int j)
	{
		
		try
		{
//			rs.next();
			rs.beforeFirst();
			
			int total = 0,vowel=0,conso=0;
			while(rs.next())
			{
					if(str.equals(rs.getString(1)) )
					{
						count++;
						dlm.addElement(count+" "+rs.getString(1)+" --> occured at index : "+ i+" "+j );
					}	
					
			}	
//		return false;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
//		return false;
	}
	
//------------------------------------for click----------------------------
	void click(int r,int c)		// when button is pressed than all lines ( row wise, column wise and diagonals ) are stored in
	{							// new matrix "aa[]" 
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


public class Word_Puzzle2 {

	public static void main(String[] args) {
		new word_puzzles1();
	}

}
