package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Connector.Connector;
import Model.LoaiPhim;
import Model.Phim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import plugin.SceneController;
import usercontrol.control.Chip;

public class AddNewMovieController implements Initializable{
    @FXML private ImageView image;
    @FXML private TextField name;
    @FXML private TextField year;
    @FXML private FlowPane genre;
    @FXML private ComboBox<String> newGenre;
    @FXML private TextField director;
    @FXML private TextField during;
    @FXML private TextField nuocsanxuat;
    @FXML private TextArea summary;
    @FXML private Button btn_dongy, btn_huy;
    
    
    ObservableList<String> list = FXCollections.observableArrayList();
	ArrayList<LoaiPhim> dsLoaiPhim=new ArrayList<LoaiPhim>();
	File f=null;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initial();
		addEvents();
	}    
	
	private void initial() {
		image.styleProperty().set("-fx-cursor: hand");
		newGenre.getItems().clear();
		dsLoaiPhim.addAll(new Connector().select(LoaiPhim.class, "select * from LOAIPHIM"));
		for(LoaiPhim lp:dsLoaiPhim) {
			list.add(lp.getTenLoai());
		}
		newGenre.setItems(list);
		image.setOnMouseClicked(e -> {
			FileChooser fileChooser = new FileChooser();
			f=fileChooser.showOpenDialog(SceneController.GetInstance().getCurrentStage());
			if(f!=null) {
				try {
					BufferedImage bimg=ImageIO.read(f);
					Image img=SwingFXUtils.toFXImage(bimg, null);
					image.setImage(img);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	@FXML
    void genreKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {			
			Chip chip = new Chip(newGenre.getValue());
			chip.setOnMouseClickedDelete(e->{
				genre.getChildren().remove(chip); 
			});
			genre.getChildren().add(chip);
			newGenre.setValue("");
		}
	}
	
	private void addEvents() {
		// TODO Auto-generated method stub
		btn_dongy.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				xuLiThemPhim();
				Stage stage=(Stage)btn_dongy.getScene().getWindow();
				stage.close();
			}
		});
		
		btn_huy.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Stage stage=(Stage)btn_huy.getScene().getWindow();
				stage.close();
			}
		});
		
	}
	
	private void xuLiThemPhim() {
		Connector<Phim> c=new Connector<Phim>();
		String maPhim="P"+c.selectPhim( "select * from PHIM").size();
		// TODO Auto-generated method stub
		String tenPhim=name.getText();
		String nuocSanXuat=nuocsanxuat.getText();
		int namSanXuat=Integer.parseInt(year.getText());
		String daoDien=director.getText();
		String thoiLuong=during.getText();
		String tomTat=summary.getText();
		String theLoai=newGenre.getValue();
		String maLoai="";
		for(LoaiPhim lp:dsLoaiPhim) {
			if(lp.getTenLoai().equals(theLoai)) {
				maLoai=lp.getMaLoai();
			}
		}
		byte[] hinhAnh=null;
		if(f!=null) {
			hinhAnh=Connector.convertFileToByte(f);
		}
		new Connector<Phim>().insert("insert into PHIM values('"+maPhim+"','"+tenPhim+"','"+nuocSanXuat+"','"+namSanXuat+"','"+theLoai+"','"+thoiLuong+"','"+daoDien+"','"+tomTat+"','"+hinhAnh+"')");
	}
	
}