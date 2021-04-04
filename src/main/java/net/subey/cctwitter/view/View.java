package net.subey.cctwitter.view;

public interface View {
    interface Post {
    }
    interface Id{
    }
    interface Get extends Id, Post {
    }
}
