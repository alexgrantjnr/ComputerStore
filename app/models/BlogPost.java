package models;

import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Model;
import play.data.validation.*;
import play.data.format.*;

@Entity
public class BlogPost extends Model{

    @SequenceGenerator(name = "blog_gen",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_gen")
    public long blogId;
    @Constraints.Required
    public String blogTitle;
    @Constraints.Required
    public String blogDescription;
    public int numLikes;
    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date postDate = new Date();

    public BlogPost(){
    }

    public BlogPost(long blogId, String blogTitle, String blogDescription, int numLikes, Date postDate) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogDescription = blogDescription;
        this.numLikes = numLikes;
        this.postDate = postDate;
    }

    //Query helper to find object with id long
    public static Finder<Long,BlogPost> find = new Finder<>(BlogPost.class);

    //Find all blog posts but orders them by date
    public static List<BlogPost> findAll(){
        return BlogPost.find.where().orderBy("blogId desc").findList();
    }

    public static BlogPost getBlogPostById(Long blogPostId){
        return find.ref(blogPostId);
    }


    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

    public void setBlogDescription(String blogDescription) {
        this.blogDescription = blogDescription;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}