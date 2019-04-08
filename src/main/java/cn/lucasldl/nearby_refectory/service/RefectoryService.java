package cn.lucasldl.nearby_refectory.service;

import cn.lucasldl.nearby_refectory.entity.Refectory;
import cn.lucasldl.nearby_refectory.util.Pagination;
import org.springframework.data.geo.GeoResults;

import java.util.List;

public interface RefectoryService {
    public String addRefectory(Refectory refectory);

    public String delRefectoryById(Long id);

    public Refectory findRefectoryById(Long id);

    public List<Refectory> findRefectoryByName(String name);

    public String updateRefectoryById(Refectory refectory);

    public Pagination<Refectory> findAll(Integer pageSize, Integer pageNum);

    public GeoResults<Refectory> findNear(double longitude, double latitude, double distance);
}
