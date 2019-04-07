package cn.lucasldl.nearby_refectory.controller;

import cn.lucasldl.nearby_refectory.entity.Refectory;
import cn.lucasldl.nearby_refectory.service.RefectoryService;
import cn.lucasldl.nearby_refectory.util.Pagination;
import org.apache.commons.lang3.StringUtils;
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
public class AdminController {
    @Autowired
    private RefectoryService refectoryService;

    /**
     * 添加餐厅信息
     * @param refectory
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public String add(Refectory refectory){
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

    /**
     * 更新餐厅信息
     * @param refectory
     * @return
     */
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
    @RequestMapping("page")
    public void page(Model model, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam("pageNum") Integer pageNum){
        Pagination<Refectory> page = refectoryService.findAll(pageSize, pageNum);
        model.addAttribute("pageResult", page);
    }


}
