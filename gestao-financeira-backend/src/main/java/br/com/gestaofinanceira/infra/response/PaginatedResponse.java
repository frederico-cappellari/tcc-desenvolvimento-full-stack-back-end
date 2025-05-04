package br.com.gestaofinanceira.infra.response;

import java.util.List;

public class PaginatedResponse<E> {
    private long total;
    private int page;
    private int pageSize;
    private int pageCount;
    private boolean ascending = true;
    private List<E> data;

    public PaginatedResponse(long total, int page, int pageSize, int pageCount, boolean ascending, List<E> data) {
        super();
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    
}