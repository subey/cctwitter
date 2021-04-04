package net.subey.cctwitter.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Schema(hidden = true)
public class PageResp<T> {
    List<T> content;
    CustomPageable pageable;

    public PageResp(Page<T> page) {
        this.content = page.getContent();
        this.pageable = new CustomPageable(
                page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(),
                page.getTotalElements());
    }
    @Data
    @Schema(hidden = true)
    class CustomPageable {
        int page;
        int size;
        long total;

        public CustomPageable(int page, int size, long total) {
            this.page = page;
            this.size = size;
            this.total = total;
        }
    }
}
