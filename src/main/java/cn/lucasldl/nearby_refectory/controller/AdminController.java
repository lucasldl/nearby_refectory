package cn.lucasldl.nearby_refectory.controller;

import cn.lucasldl.nearby_refectory.entity.Refectory;
import cn.lucasldl.nearby_refectory.service.RefectoryService;
import cn.lucasldl.nearby_refectory.service.impl.RefectoryServiceImpl;
import cn.lucasldl.nearby_refectory.util.Pagination;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/admin/")
@Api(tags = "管理员接口")
public class AdminController {
    @Autowired
    private RefectoryService refectoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RefectoryServiceImpl.class);

    /**
     * 添加餐厅信息
     * @param refectory
     * @return
     */
    @ApiOperation("添加餐厅")
    @ApiImplicitParam(name = "refectory", value = "餐厅信息实体", dataType = "Refectory")
    @RequestMapping("add")
    @ResponseBody
    public String add(Refectory refectory){
        LOGGER.info("添加controller：" + refectory.toString());
        if (StringUtils.isEmpty(refectory.getTitle()) || StringUtils.isEmpty(Arrays.toString(refectory.getLocation())) || StringUtils.isEmpty(refectory.getIntroduce())){
            return "所有字段不能为空";
        }
        return refectoryService.addRefectory(refectory);
    }

    /**
     * 删除餐厅信息
     * @param id
     * @return
     */
    @ApiOperation("根据id删除餐厅")
    @ApiImplicitParam(name = "id", value = "餐厅id", dataType = "long", example = "201803131245")
    @RequestMapping("del")
    @ResponseBody
    public String del(@RequestParam("id") Long id){
        return refectoryService.delRefectoryById(id);
    }

    /**
     * 根据id查找餐厅信息
     * @param model
     * @param id
     * @return
     */
    @ApiOperation("根据id查找餐厅信息")
    @ApiImplicitParam(name = "id", value = "餐厅id", dataType = "long", example = "201803131245")
    @RequestMapping("{id}")
    @ResponseBody
    public String findById(Model model, @PathVariable("id") Long id){
        Refectory refectory = refectoryService.findRefectoryById(id);
        if(refectory != null){
            model.addAttribute("refectory", refectory);
            return "查询成功";
        }
        return "查询失败，请检查id";
    }

    @ApiOperation("根据名字对餐厅名进行模糊查询")
    @ApiImplicitParam(name = "name", value = "餐厅名", dataType = "String")
    @RequestMapping("name")
    public String findByName(Model model, @RequestParam("name") String name){
        model.addAttribute("result", refectoryService.findRefectoryByName(name));
        model.addAttribute("isPage", 1);
        return "/admin/index";
    }

    /**
     * 更新餐厅信息
     * @param refectory
     * @return
     */
    @ApiOperation("更新餐厅信息")
    @ApiImplicitParam(name = "refectory", value = "餐厅信息实体", dataType = "Refectory")
    @RequestMapping("update")
    @ResponseBody
    public String update(Refectory refectory){
        return refectoryService.updateRefectoryById(refectory);
    }

    /**
     * 分页查询餐厅列表
     * @param model
     * @param pageSize 每页大小
     * @param pageNum 页码
     */
    @ApiOperation("分页查询餐厅列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10", dataType = "int", example = "10"),
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", dataType = "int", example = "2")
    })
    @RequestMapping("index")
    public String page(Model model, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        Pagination<Refectory> page = refectoryService.findAll(pageSize, pageNum);
        model.addAttribute("result", page);
        model.addAttribute("isPage", 0);
        return "/admin/index";
    }

}
