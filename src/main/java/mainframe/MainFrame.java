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
import action.RegisterAction;
import action.StausChangeAction;
import utils.RunningList;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class MainFrame {

	private JFrame mainframe;
	private JTextField sosUrl;
	private JTree allSensorTree;
	private JTree runingSensorTree;
	private JLabel lblurl;
	private String sensorname;
	private String sensortype;
	public static JTextArea responseArea;
	private JTextField ssnsUrl;

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
					DefaultMutableTreeNode node_2;
					node_1 = new DefaultMutableTreeNode("地质灾害");
						node_2 = new DefaultMutableTreeNode("测斜仪");
							node_2.add(new DefaultMutableTreeNode("Inclinometer_1"));
							node_2.add(new DefaultMutableTreeNode("Inclinometer_2"));
							node_2.add(new DefaultMutableTreeNode("Inclinometer_3"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("沉降仪");
							node_2.add(new DefaultMutableTreeNode("Settlement_1"));
							node_2.add(new DefaultMutableTreeNode("Settlement_2"));
							node_2.add(new DefaultMutableTreeNode("Settlement_3"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("水资源");
						node_2 = new DefaultMutableTreeNode("水位传感器");
							node_2.add(new DefaultMutableTreeNode("waterlevel_1"));
							node_2.add(new DefaultMutableTreeNode("waterlevel_2"));
							node_2.add(new DefaultMutableTreeNode("waterlevel_3"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("雨量传感器");
							node_2.add(new DefaultMutableTreeNode("rainfall_1"));
							node_2.add(new DefaultMutableTreeNode("rainfall_2"));
							node_2.add(new DefaultMutableTreeNode("rainfall_3"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("气候");
						node_2 = new DefaultMutableTreeNode("温度传感器");
							node_2.add(new DefaultMutableTreeNode("Temperature_1"));
							node_2.add(new DefaultMutableTreeNode("Temperature_2"));
							node_2.add(new DefaultMutableTreeNode("Temperature_3"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("风速传感器");
							node_2.add(new DefaultMutableTreeNode("windspeed_1"));
							node_2.add(new DefaultMutableTreeNode("windspeed_2"));
							node_2.add(new DefaultMutableTreeNode("windspeed_3"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("风向传感器");
							node_2.add(new DefaultMutableTreeNode("winddirection_1"));
							node_2.add(new DefaultMutableTreeNode("winddirection_2"));
							node_2.add(new DefaultMutableTreeNode("winddirection_3"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("生态环境");
						node_2 = new DefaultMutableTreeNode("湿度传感器");
							node_2.add(new DefaultMutableTreeNode("Humidity_1"));
							node_2.add(new DefaultMutableTreeNode("Humidity_2"));
							node_2.add(new DefaultMutableTreeNode("Humidity_3"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("CO2浓度传感器");
							node_2.add(new DefaultMutableTreeNode("CO2_1"));
							node_2.add(new DefaultMutableTreeNode("CO2_2"));
							node_2.add(new DefaultMutableTreeNode("CO2_3"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("城市交通");
						node_2 = new DefaultMutableTreeNode("GPS");
							node_2.add(new DefaultMutableTreeNode("taxigps_1"));
							node_2.add(new DefaultMutableTreeNode("taxigps_2"));
							node_2.add(new DefaultMutableTreeNode("taxigps_3"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("视频");
							node_2.add(new DefaultMutableTreeNode("videocarera_1"));
							node_2.add(new DefaultMutableTreeNode("videocarera_2"));
							node_2.add(new DefaultMutableTreeNode("videocarera_3"));
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode("图像");
							node_2.add(new DefaultMutableTreeNode("imagecamera_1"));
							node_2.add(new DefaultMutableTreeNode("imagecamera_2"));
							node_2.add(new DefaultMutableTreeNode("imagecamera_3"));
						node_1.add(node_2);
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
				if (sensorname == null || !sensorname.matches("[a-zA-Z0-9_]+")) {
					JOptionPane.showMessageDialog(mainframe, "请选择传感器", "标题",JOptionPane.WARNING_MESSAGE);  
					return;
				} else if (sosUrl.getText().equals("")) {
					JOptionPane.showMessageDialog(mainframe, "输入服务地址", "标题",JOptionPane.WARNING_MESSAGE);  
				}

				String sosurl = sosUrl.getText();
				Boolean boolean1 =  ButtonAction.start(sensortype, sensorname, sosurl);

				
				if (boolean1) {
					RunningList.runningList.add(sensorname);
					String endpoint = ssnsUrl.getText();
					RegisterAction registerAction = new RegisterAction();
					registerAction.register("procedure_"+sensorname, sosurl, endpoint);
					StausChangeAction stausChangeAction = new StausChangeAction();
					stausChangeAction.start("procedure_"+sensorname, endpoint);
				} else {
					JOptionPane.showMessageDialog(mainframe, "已在运行", "标题",JOptionPane.WARNING_MESSAGE);
				}
				refreshTree();
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
					String endpoint = ssnsUrl.getText();
					StausChangeAction stausChangeAction = new StausChangeAction();
					stausChangeAction.stop("procedure_"+ sensorname, endpoint);
				}
			}
		});
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		buttonPanel.add(btnStart);
		buttonPanel.add(btnStop);
		
		lblurl = new JLabel("SOS URL：");
		lblurl.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
		lblurl.setSize(300, 10);
		buttonPanel.add(lblurl);
		
		sosUrl = new JTextField();
		sosUrl.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		sosUrl.setHorizontalAlignment(SwingConstants.LEFT);
		sosUrl.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
		sosUrl.setText("http://localhost:8080/52n-sos-webapp/service");
		buttonPanel.add(sosUrl);
		sosUrl.setColumns(15);
		
		JLabel lblNewLabel = new JLabel("SSNS URL");
		lblNewLabel.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
		buttonPanel.add(lblNewLabel);
		
		ssnsUrl = new JTextField();
		ssnsUrl.setText("http://localhost:8080/SSNS/service");
		ssnsUrl.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 15));
		ssnsUrl.setHorizontalAlignment(SwingConstants.LEFT);
		buttonPanel.add(ssnsUrl);
		ssnsUrl.setColumns(15);
		
		runingSensorAndXMLPanel.setLeftComponent(runingSensorTree);
	}
}
