package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

public class product extends Model{


    private long id;
    private String name;
    private double price;
    private String description;
    private int quantity;




}