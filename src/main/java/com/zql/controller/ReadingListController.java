package com.zql.controller;

import com.zql.config.TaobaoProperties;
import com.zql.entity.Book;
import com.zql.repository.ReadingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Controller
@RequestMapping("/readinglist")
public class ReadingListController {

    @Autowired
    private ReadingListRepository readingListRepository;

    @Autowired
    private TaobaoProperties taobaoProperties;

    @RequestMapping(value = "/{reader}",method = RequestMethod.GET)
    public String readersBooks(@PathVariable(value = "reader") String reader,Model model){
        List<Book> readingList = readingListRepository.findByReader(reader);
        if(readingList!=null){
            model.addAttribute("books",readingList);
        }
        model.addAttribute("taobao",this.taobaoProperties);
        return "readingList";
    }

    @RequestMapping(value="/{reader}", method=RequestMethod.POST)
    @ResponseBody
    public String addToReadingList( @PathVariable(value = "reader")String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/readinglist/{reader}";
    }



}
