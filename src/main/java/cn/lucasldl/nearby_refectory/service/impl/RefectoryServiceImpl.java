package cn.lucasldl.nearby_refectory.service.impl;

import cn.lucasldl.nearby_refectory.entity.Refectory;
import cn.lucasldl.nearby_refectory.service.RefectoryService;
import cn.lucasldl.nearby_refectory.util.Const;
import cn.lucasldl.nearby_refectory.util.Pagination;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Transactional
@Service("refectoryService")
public class RefectoryServiceImpl implements RefectoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefectoryServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加餐厅信息
     * @param refectory 餐厅信息实体
     * @return 操作后的提示
     */
    @Override
    public String addRefectory(Refectory refectory){
        //使用时间戳作为主键
        String id = DateFormatUtils.format(new Date(), Const.DATE_FORMATE);
        refectory.setId(Long.parseLong(id));
        Refectory resultRefectory = mongoTemplate.save(refectory);
        if(resultRefectory != null && resultRefectory.getId().equals(Long.parseLong(id))){
            LOGGER.info("添加成功:" + resultRefectory.toString());
            return "添加成功";
        }
        return "添加失败";
    }

    /**
     * 根据id删除餐厅信息
     * @param id 餐厅id，long类型
     * @return 操作后的提示
     */
    @Override
    public String delRefectoryById(Long id){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        DeleteResult result = mongoTemplate.remove(query, Refectory.class);
        if(result.getDeletedCount() == 1){
            LOGGER.info("删除成功:" + result.toString());
            return "删除成功";
        }
        return "删除失败";

    }

    /**
     * 根据id查找餐厅信息
     * @param id 餐厅id，long类型
     * @return 餐厅信息，可为null
     */
    @Override
    public Refectory findRefectoryById(Long id){
        Refectory refectory = mongoTemplate.findById(id, Refectory.class);
        if(refectory != null) {
            LOGGER.info("查询结果:" + refectory.toString());
        } else {
            LOGGER.info("不存在此id的餐厅:" + id);
        }
        return(refectory);
    }

    @Override
    public List<Refectory> findRefectoryByName(String name){
        Query query = new Query();
//        Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("title").regex(name));
        List<Refectory> list = mongoTemplate.find(query, Refectory.class);
        return list;
    }

    /**
     * 根据id更新餐厅信息，如果更新的信息与原来全部相同，则更新失败
     * @param refectory 餐厅信息实体
     * @return 操作后的提示
     */
    @Override
    public String updateRefectoryById(Refectory refectory){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(refectory.getId()));
        Update update = Update.update("title", refectory.getTitle())
                                .set("location", refectory.getLocation())
                                .set("introduce", refectory.getIntroduce())
                                .set("address", refectory.getAddress())
                                .set("phone", refectory.getPhone());
        UpdateResult result = mongoTemplate.upsert(query, update, Refectory.class);
        if(result.getModifiedCount() == 0){
            LOGGER.info("更新失败:" + result);
            return "更新失败，请检查是否存在此餐厅，或者信息是否没变";
        } else {
            LOGGER.info("更新成功:" + result);
            return "更新成功";
        }
    }

    /**
     * 查询餐厅列表，进行分页操作
     * @param pageSize 每页数量
     * @param pageNum 页码
     * @return 分页数据
     */
    @Override
    public Pagination<Refectory> findAll(Integer pageSize, Integer pageNum){
        Pagination<Refectory> pageResult = new Pagination<>();
        Query query = new Query();
        //以id排序
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "id")));
        //总的记录
        Long count = mongoTemplate.count(query, Refectory.class);
        //计算总页数
        Integer pages = (int) Math.ceil(count / (double) pageSize);
        if(pageNum <= 0 || pageNum > pages){
            pageNum = 1;
        }
        //以跳页的形式分页
        int skip = pageSize * (pageNum - 1);
        query.skip(skip).limit(pageSize);
        List<Refectory> refectoryList = mongoTemplate.find(query, Refectory.class);
        pageResult.setTotal(count);
        pageResult.setPages(pages);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setList(refectoryList);
        return pageResult;
    }

    /**
     * 根据自己的坐标查找附近的餐厅，可选择范围
     * @param longitude 经度
     * @param latitude 维度
     * @param distance 查找范围
     * @return Geo封装的结果集
     */
    public GeoResults<Refectory> findNear(double longitude, double latitude, double distance){
        NearQuery nearQuery = NearQuery.near(longitude, latitude, Metrics.KILOMETERS);
        nearQuery.maxDistance(distance);
        GeoResults<Refectory> results = mongoTemplate.geoNear(nearQuery, Refectory.class);
        return results;
    }
}
