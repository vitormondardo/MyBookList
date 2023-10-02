package com.example.application.data.entity;

public class Books {
    //ATRIBUTOS -----------------------------------
    private Integer id;
    private String bookName;
    private String autorName;
    private String genre;
    private String theme;
    private String startDate;
    private String finishDate;
    private boolean favorite;

    //MÉTODOS -------------------------------------
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
   
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Books)) {
            return false;
        }
        Books other = (Books) obj;
        return id == other.id;
    }

    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAutorName() {
        return autorName;
    }
    public void setAutorName(String autorName) {
        this.autorName = autorName;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public boolean getFavorite() {
        return favorite;
    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


}