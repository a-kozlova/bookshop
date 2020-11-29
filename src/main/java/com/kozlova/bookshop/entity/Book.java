package com.kozlova.bookshop.entity;

public class Book implements Comparable<Book> {
    private String title;
    private double price;
    private int pages;
    private Genre genre;
    private String isbn;

    private Book(Builder builder) {
        this.title = builder.title;
        this.price = builder.price;
        this.pages = builder.pages;
        this.genre = builder.genre;
        this.isbn = builder.isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        result = prime * result + pages;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (pages != other.pages)
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %s%nISBN: %s%n%d Pages, %n%f Euro", this.title, this.genre.toString(), 
                this.isbn, this.pages, this.price);
    }
    

    @Override
    public int compareTo(Book otherBook) {
        return this.title.compareTo(otherBook.getTitle());
    }    

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private double price;
        private int pages;
        private Genre genre;
        private String isbn;

        private Builder() {
        }

        public Book build() {
            return new Book(this);
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder withPages(int pages) {
            this.pages = pages;
            return this;
        }

        public Builder withGenre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public Builder withIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

    }

}
