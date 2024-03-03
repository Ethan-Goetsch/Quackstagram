package Refactored;

import java.time.LocalDateTime;

public class Content
{
    private String text;
    private Picture picture;
    private LocalDateTime dateCreated;

    private Content(String text, Picture picture, LocalDateTime dateCreated)
    {
        this.dateCreated = dateCreated;
        this.text = text;
        this.picture = picture;
    }

    public String getText() { return text; }
    public Picture getPicture() { return picture; }
    public LocalDateTime getDateCreated() { return dateCreated; }

    public void setText(String text) { this.text = text; }
    public void setPicture(Picture picture) { this.picture = picture; }

    private void setDateCreated(LocalDateTime dateCreated) {this.dateCreated = dateCreated; }

    public class Builder
    {
        private Content content;
        private Builder context;

        public Builder()
        {
            content = new Content("", null, LocalDateTime.now());
            context = this;
        }

        public Builder withText(String text)
        {
            content.setText(text);
            return context;
        }

        public Builder withPicture(Picture picture)
        {
            content.setPicture(picture);
            return context;
        }

        public Builder withDate(LocalDateTime dateTime)
        {
            content.setDateCreated(dateTime);
            return context;
        }

        public Content build()
        {
            return content;
        }
    }
}