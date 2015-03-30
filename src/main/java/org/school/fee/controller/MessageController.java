package org.school.fee.controller;

import org.bson.types.ObjectId;
import org.school.fee.models.Message;
import org.school.fee.models.User;
import org.school.fee.service.MessageService;
import org.school.fee.support.utils.Result;
import org.school.fee.support.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {
	@Autowired
	MessageService messageService;
	
	@RequestMapping("/index/{page}")
	public ModelAndView list(@PathVariable Integer page,Integer pageSize){
		User user = ShiroUtils.getCurrentUser();
		Page<Message> result = messageService.listMessage(page, pageSize, user.getId());
		return new ModelAndView("/message/index").addObject("result",result);
	}
	
	@RequestMapping("/index")
	public ModelAndView list(){
		return new ModelAndView("forward:/action/message/index/0");
	}
	
	@RequestMapping("/updatestatus")
	public ModelAndView update(){
		User user = ShiroUtils.getCurrentUser();
		messageService.updateAllNewMessage(user.getId());
		return new ModelAndView().addObject("result", new Result("更新成功","success"));
	}
	
	@RequestMapping("/getcount")
	public ModelAndView getCount(){
		ModelAndView  mv = new ModelAndView();
		User user = ShiroUtils.getCurrentUser();
		long count = messageService.countNewMessage(user.getId());
		return mv.addObject("result",new Result("success",count));
	}
	@RequestMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable ObjectId id){
		messageService.deleteMessage(id);
		return new ModelAndView().addObject("result", new Result("删除成功","success"));
	}
}
