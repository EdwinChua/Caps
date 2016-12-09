package edu.iss.caps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.model.User;
import edu.iss.caps.service.CourseService;
//import edu.iss.caps.service.StudentService;
import edu.iss.caps.service.EnrolmentService;
import edu.iss.caps.service.StudentService;



@RequestMapping(value="/Course")
@Controller
public class StudentController {

	@Autowired
	private CourseService cService;
	@Autowired
	private StudentService sService;
	@Autowired
	private EnrolmentService eService;
// 	@Autowired
//	private DepartmentValidator dValidator;
	
//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public ModelAndView newEmployeePage() {
//		ModelAndView mav = new ModelAndView("employee-new", "employee", new ());
//		mav.addObject("eidlist", cService.findAllEmployeeIDs());
//		return mav;
//	}
	@RequestMapping(value="/sec",method = RequestMethod.GET)
	public String testMestod(HttpServletRequest request){
	    User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
	    
	    return "sec";
	  }
//	
//	@SuppressWarnings("null")

	@RequestMapping(value = "/a",method = RequestMethod.GET)
	public ModelAndView AvailablePage1(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("course-available");
		List<Course> courselist = cService.findAllCourses();
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		List<Enrolment> grades = eService.findCourseBySID(s);
		
		
			for (Enrolment enrolment : grades) {
				courselist.remove(enrolment.getCourses());
			}
		
		
		mav.addObject("courseavailable", courselist);
		return mav;
	}
	
///////////////////////////listall courses
	
	@RequestMapping(value = "/listall", method = RequestMethod.GET)
	public ModelAndView CourseListPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("list-all");
		List<Course> courselist = cService.findAllCourses();
		mav.addObject("courselist", courselist);
		return mav;
	}
	

	

///////chunxiao need to refine this part


	
	@RequestMapping(value = "/grade",method = RequestMethod.GET)
	public ModelAndView studentviewgrade(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("list-grade");
		User u = (User) request.getSession().getAttribute("user");
		String s = u.getUserId();
		List<Enrolment> grades = eService.findCourseBySID(s);/////////joe changed in eservice
		
		
		
		mav.addObject("grlist", grades);
		return mav;
	}
	
	
	
	@RequestMapping(value = "/enrol/{courseId}", method = RequestMethod.GET)
	public ModelAndView enrolling(@ModelAttribute Course course, BindingResult result,HttpServletRequest request,
			@PathVariable int courseId, final RedirectAttributes redirectAttributes) {

		
		   Course c = cService.findCourse(courseId);
		   String message ="";
		
		   

		if (c.getSize()>c.getCurrentEnrollment()) {
			User u = (User) request.getSession().getAttribute("user");
			String s = u.getUserId();
		    StudentDetail studentDetail =sService.findStudentById(s);
  		    eService.createEnrollment(studentDetail, c);/////////need to check in eservice
  		    cService.increasecourseEnrolment(c);/////////need to check in cservice
  		    
		    message = "You have sucessfully enrolled with NO. " + courseId + " course ";
			redirectAttributes.addFlashAttribute("message", message);
			
			
			
			
		}else {
		    message = " NO. " + courseId + " course is aready full!!!";
			redirectAttributes.addFlashAttribute("message", message);		
			
		}
		request.getSession().setAttribute("message", message);
//		Session session = SetAttribute("message", );
		ModelAndView mav = new ModelAndView("redirect:/Course/bar");
		
		return mav;
	}
	@RequestMapping(value = "/bar",method = RequestMethod.GET)
	public ModelAndView bar(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView("bar");
		String m=(String)request.getSession().getAttribute("message");
	
		
		mav.addObject("message", m);
		return mav;
	}
	
		
}
