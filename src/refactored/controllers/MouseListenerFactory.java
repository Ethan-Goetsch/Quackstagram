package refactored.controllers;

import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import refactored.entities.Post;

public interface MouseListenerFactory {
    public MouseListener createMouseClickListener(ImageIcon imageIcon, Post post);
}
