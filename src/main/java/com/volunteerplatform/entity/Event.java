package com.volunteerplatform.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String name;

	@NotNull
	private LocalDate date;

	@NotNull
	private LocalTime time;

	@NotEmpty
	private String description;

	@ManyToMany
    @JoinTable(
        name = "event_enrollment",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> enrolledUsers;

	public Event() {
		super();
	}

	public Event(Long id, @NotEmpty String name, @NotNull LocalDate date, @NotNull LocalTime time,
			@NotEmpty String description, Set<User> enrolledIds) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.time = time;
		this.description = description;
		this.enrolledUsers = enrolledIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getEnrolledUserIds() {
		return enrolledUsers;
	}

	public void setEnrolledUserIds(Set<User> enrolledIds) {
		this.enrolledUsers = enrolledIds;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", date=" + date + ", time=" + time + ", description="
				+ description + ", enrolledUserIds=" + enrolledUsers + "]";
	}
}
