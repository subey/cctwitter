package net.subey.cctwitter.view;

import org.springframework.beans.factory.annotation.Value;

public interface TimelineView {
    @Value("#{target.user}")
    String getUser();
    String getBody();
}
