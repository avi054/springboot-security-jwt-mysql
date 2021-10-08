package edu.aviral.dao;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "usertable")	//optional
public class User {

	@Id
	@GeneratedValue		//optional
	private Integer id;
	private String name;
	private String username;
	private String password;

	//=============== Separate child table will be created =========================//
	@ElementCollection(fetch = FetchType.EAGER)//To avoid LazyInitialization
	@CollectionTable(name = "rolestable", joinColumns = @JoinColumn(name = "id")) //optional
	@Column(name = "role")		//optional
	private Set<String> roles;  // [] in json
	//=============================================================================//
}
