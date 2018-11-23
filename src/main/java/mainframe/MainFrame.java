package mainframe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JMenu;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTree;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.tree.DefaultTreeModel;

import action.ButtonAction;
import utils.RunningList;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class MainFrame {

	private JFrame mainframe;
	private JTextField serviceUrl;
	private JTree allSensorTree;
	private JTree runingSensorTree;
	private JLabel lblurl;
	private String sensorname;
	private String sensortype;
	public static JTextArea responseArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.mainframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//更新runningTree
	public void refreshTree() {

		runingSensorTree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("运行中传感器") {
				{
					for(String node:RunningList.runningList)
					add(new DefaultMutableTreeNode(node));
				}
			}
		));
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainframe = new JFrame();
		mainframe.setResizable(false);
		mainframe.setType(Type.UTILITY);
		mainframe.setTitle("模拟传感器");
		mainframe.setBounds(100, 100, 450, 300);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.getContentPane().setLayout(new BorderLayout(10, 10));
		mainframe.setSize(1000, 600);
		
		JPanel buttonPanel = new JPanel();
		mainframe.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		
		
		
		JPanel sensorTreePanel = new JPanel();
		mainframe.getContentPane().add(sensorTreePanel, BorderLayout.WEST);
		sensorTreePanel.setLayout(null);
		sensorTreePanel.setPreferredSize(new Dimension(200, 0));

		allSensorTree = new JTree();
		allSensorTree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("传感器列表") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("原位传感器");
						
						node_1.add(new DefaultMutableTreeNode("detector1"));
						node_1.add(new DefaultMutableTreeNode("detector2"));
						node_1.add(new DefaultMutableTreeNode("detector3"));
						node_1.add(new DefaultMutableTreeNode("detector4"));
						node_1.add(new DefaultMutableTreeNode("detector5"));
						node_1.add(new DefaultMutableTreeNode("detector6"));
						node_1.add(new DefaultMutableTreeNode("detector7"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("移动传感器");
						node_1.add(new DefaultMutableTreeNode("taxi1"));
						node_1.add(new DefaultMutableTreeNode("taxi2"));
						node_1.add(new DefaultMutableTreeNode("taxi3"));
						node_1.add(new DefaultMutableTreeNode("taxi4"));
						node_1.add(new DefaultMutableTreeNode("taxi5"));
						node_1.add(new DefaultMutableTreeNode("taxi6"));
						node_1.add(new DefaultMutableTreeNode("taxi7"));
					add(node_1);
				}
			}
		));
		allSensorTree.setBounds(0, 0, 200, 518);
		allSensorTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) allSensorTree.getLastSelectedPathComponent();
				sensorname = node.toString();//获得这个结点的名称
			    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
			    sensortype = parent.toString();
			}
		});
		
		
		
		sensorTreePanel.add(allSensorTree);
		
		JSplitPane runingSensorAndXMLPanel = new JSplitPane();
		runingSensorAndXMLPanel.setResizeWeight(0.4);
		runingSensorAndXMLPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainframe.getContentPane().add(runingSensorAndXMLPanel, BorderLayout.CENTER);

		
		responseArea = new JTextArea();
		responseArea.setText("响应结果：\r\n\r\n");
		responseArea.setEditable(false);
		responseArea.setBackground(new Color(255, 204, 204));
		runingSensorAndXMLPanel.setRightComponent(responseArea);
		responseArea.setLineWrap(true); 
		
		runingSensorTree = new JTree();
		runingSensorTree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("运行中传感器") {}
		));
		
		
		JButton btnStart = new JButton("开始");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO:
				if (sensorname == null || sensorname.equals("原位传感器") || sensorname.equals("移动传感器")) {
					JOptionPane.showMessageDialog(mainframe, "请选择传感器", "标题",JOptionPane.WARNING_MESSAGE);  
					return;
				} else if (serviceUrl.getText().equals("")) {
					JOptionPane.showMessageDialog(mainframe, "输入服务地址", "标题",JOptionPane.WARNING_MESSAGE);  
				}

				String sosurl = serviceUrl.getText();
				Boolean boolean1 =  ButtonAction.start(sensortype, sensorname, sosurl);
				RunningList.runningList.add(sensorname);
				refreshTree();
				if (boolean1) {
					
				} else {
					JOptionPane.showMessageDialog(mainframe, "已在运行", "标题",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JButton btnStop = new JButton("停止");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO:
				Boolean boolean1 = ButtonAction.stop(sensorname);
				RunningList.runningList.remove(sensorname);
				refreshTree();
				if (!boolean1) {
					JOptionPane.showMessageDialog(mainframe, "没有运行", "标题",JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(mainframe, "已停止", "标题",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		buttonPanel.add(btnStart);
		buttonPanel.add(btnStop);
		
		lblurl = new JLabel("服务URL：");
		lblurl.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
		lblurl.setSize(300, 10);
		buttonPanel.add(lblurl);
		
		serviceUrl = new JTextField();
		serviceUrl.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		serviceUrl.setHorizontalAlignment(SwingConstants.LEFT);
		serviceUrl.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
		serviceUrl.setText("http://localhost:8080/52n-sos-webapp/service");
		buttonPanel.add(serviceUrl);
		serviceUrl.setColumns(12);
		
		runingSensorAndXMLPanel.setLeftComponent(runingSensorTree);
	}
}
