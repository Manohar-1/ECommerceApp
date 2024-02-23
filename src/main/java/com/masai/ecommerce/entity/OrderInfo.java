package com.masai.ecommerce.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class OrderInfo{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "orderInfo", cascade = CascadeType.ALL)
    private List<CartItem> items;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", items=" + items + ", user=" + user + "]";
	}
    
    
}
