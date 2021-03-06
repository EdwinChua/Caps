package edu.iss.caps.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.Enrolment;
import edu.iss.caps.model.LecturerDetail;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.model.User;
import edu.iss.caps.service.CourseService;
import edu.iss.caps.service.EnrolmentService;
import edu.iss.caps.service.LecturerService;
import edu.iss.caps.service.StudentService;
import edu.iss.caps.service.UserService;;

@RequestMapping("/admin")
@Controller
public class AdminController
{
	// Edwin

	@Autowired
	CourseService cseService;
	@Autowired
	LecturerService lecturerService;
	@Autowired
	UserService userService;
	@Autowired
	StudentService studentService;
	@Autowired
	EnrolmentService enrolmentService;
	
//	--------------------------------------------
//	
//	Manage Student Section
//	
//	--------------------------------------------

	//	--------------------------------------------
	// Manage Student Views - All views are processed here, pagination handled here
	//	--------------------------------------------
	@RequestMapping(value = {"/managestudent/{type}", "/managestudent"}, method = RequestMethod.GET)
	public ModelAndView manageStudent(@PathVariable Map<String, String> pathVariablesMap, Locale locale, Model model, @RequestParam Map<String, String> requestParams , HttpServletRequest req)
	{
		try //tries to get ID params, and return 1 result
		{
			String id = requestParams.get("id").toLowerCase();
			ModelAndView mav = new ModelAndView("managestudent");
			StudentDetail student = studentService.findStudentById(id);
			
			// retrieves list of courses student is enrolled in
			ArrayList<Enrolment> enrolList = enrolmentService.findAllCoursesAttending();
			List<Enrolment> enrolList2 = new ArrayList<Enrolment>();
			for (Enrolment e : enrolList)
			{
				if (e.getStudentDetails().getStudentId().toLowerCase().contains(id))
				{
					enrolList2.add(e);
				}
			}

			// lists all courses available for student to enrol in
			ArrayList<Course> courseList = cseService.findAllCourses();
			ArrayList<Course> courseListTemp = new ArrayList<Course>();
			for (Course c : courseList)
			{
				if (c.getStatus().equals("Open"))
				{
					courseListTemp.add(c);
				}
			}
			//removes courses student is already enrolled in
			for (Enrolment e : enrolList2)
			{
				courseListTemp.remove(e.getCourses());
			}

			mav.addObject("courseavailable", courseListTemp);
			mav.addObject("enroldata", enrolList2);
			mav.addObject("data", student); //adds specific student detail to response
			return mav;
		} catch (Exception e) // catches requestParams.get("id") null pointer exception, lists all students
		{

			
			PagedListHolder<StudentDetail> studentList = null;

			String type = pathVariablesMap.get("type");

			if (null == type) {
				// First Request, Return first set of list
				List<StudentDetail> studentListOther = studentService.findAllStudents();
				studentList = new PagedListHolder<StudentDetail>();
				studentList.setSource(studentListOther);
				studentList.setPageSize(10);
				req.getSession().setAttribute("studentListPage", studentList);
			} else {
				// Return specific index set of list
				studentList = (PagedListHolder<StudentDetail>) req.getSession().getAttribute("studentListPage");
				int pageNum = Integer.parseInt(type);
				studentList.setPage(pageNum);
			}

			ModelAndView mv = new ModelAndView("managestudent");
			return mv;
		}
	}

	//	--------------------------------------------
	// Create Student - Creates User, then Student, and redirects to Manage Student
	//	--------------------------------------------
	@RequestMapping(value = "/createstudent", method = RequestMethod.POST)
	public String createStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

		String id = requestParams.get("id");
		if (userService.findUser(id) != null) // Ensures User with ID does not exist already
		{
			return "redirect:managestudent?actionstatus=userexisterror&id=create";
		}
		else
		{
			String firstName = requestParams.get("firstName");
			String lastName = requestParams.get("lastName");
			String password = requestParams.get("password");
			String email = requestParams.get("emailinput");
			String dateString = requestParams.get("dateinput");
			String status = "Active";
			String role = "Student";
			
			// Passes data string to method below for processing
			Date enrolmentDate = ConvertToDate(dateString);
	
			// create user
			User user = new User(id, password, role);
			userService.createUser(user);
			
			//verifies that user is successfully created
			User userTemp = userService.findUser(id);
			String id2 = userTemp.getUserId();
			
			// create student with reference to user
			StudentDetail student = new StudentDetail(id2, firstName, lastName, enrolmentDate, status, email);
			student.setStatus("Active");
			studentService.createStduent(student);
	
			return "redirect:managestudent?actionstatus=createsuccess";
		}
	}

	//	--------------------------------------------
	// Update Student + Student User Details, and redirects to Manage Student
	//	--------------------------------------------
	@RequestMapping(value = "/updatestudent", method = RequestMethod.POST)
	public String updateStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("id");
		String firstName = requestParams.get("firstName");
		String lastName = requestParams.get("lastName");
		String password = requestParams.get("password");
		String email = requestParams.get("emailinput");
		String role = "Student";

		if (password.length() > 1) // If password length <1 , not updated. MAy be updated depending on requirement
		{
			User user = new User(id, password, role);
			userService.changeUser(user);
		}
		User userTemp = userService.findUser(id);
		id = userTemp.getUserId();

		StudentDetail student = studentService.findStudentById(id);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		studentService.changeStudent(student);

		return "redirect:managestudent?actionstatus=success";
	}

	//	--------------------------------------------
	// Soft delete Student Account - Marked Disabled , and redirects to Manage Student
	//	--------------------------------------------
	@RequestMapping(value = "/deletestudent", method = RequestMethod.POST)
	public String deleteStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("deletethis");
		StudentDetail student = studentService.findStudentById(id);
		student.setStatus("Disabled");
		studentService.changeStudent(student);
		
		return "redirect:managestudent?actionstatus=success";
	}

	//	--------------------------------------------
	// Mark Student as removed from course ( In enrolment table), and redirects to Manage Student
	//	--------------------------------------------
	@RequestMapping(value = "/removestudentenrolment", method = RequestMethod.POST)
	public String removeStudentFromEnrolment(Locale locale, Model model,
			@RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("removethis"));
		String studentId = requestParams.get("removethisbyId");
		Enrolment enrolment = enrolmentService.findbyEnrolmentId(id);
		enrolment.setStatus("Removed");
		enrolment.setGrade("N/A");

		enrolmentService.updateEnrolment(enrolment);
		AddCourseEnrolmentCounter(enrolment.getCourses(), false);

		return "redirect:managestudent?actionstatus=success&id=" + studentId;
	}

	//	--------------------------------------------
	// Add course via student table - Redirects to Manage Student / specific student profile view
	//	--------------------------------------------
	@RequestMapping(value = "/addcoursetostudent", method = RequestMethod.GET)
	public String addCourseToStudent(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String studentId = requestParams.get("studentId");
		String courseTitle = requestParams.get("courseId");
		String[] sArray = courseTitle.split(" -");
		int courseId = Integer.parseInt(sArray[0]);
		Course course = cseService.findCourse(courseId);

		//only allows student to be enrolled for course if course is not full
		if (course.getCurrentEnrollment() < course.getSize())
		{
			StudentDetail studentDetail = studentService.findStudentById(studentId);
			enrolmentService.createEnrollment(studentDetail, course);
			AddCourseEnrolmentCounter(course, true);
			return "redirect:managestudent?actionstatus=success&id=" + studentId;
		} else
		{
			return "redirect:managestudent?actionstatus=coursefull&id=" + studentId;
		}
	}

	//	--------------------------------------------
	// Search Student - Returns full list of Students, uses controller to iterate through array
	// (Inefficient, but benefits from not having to write additional queries) - Generates own view
	// Has pagination
	//	--------------------------------------------
	@RequestMapping(value = {"/searchstudent","/searchstudent/{type}"}, method = RequestMethod.GET)
	public ModelAndView searchStudent(@PathVariable Map<String, String> pathVariablesMap, Locale locale, Model model, @RequestParam Map<String, String> requestParams , HttpServletRequest req)
	{
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		String accountstatus = requestParams.get("accountstatus").toLowerCase();
		
		// Ensures that correct account status is passed into variable 'accountstatus'
		if (accountstatus.contains("all")) 
		{
			accountstatus = "";
		}
		ModelAndView mav = new ModelAndView("managestudent");
		ArrayList<StudentDetail> lctList = studentService.findAllStudents();
		List<StudentDetail> searchList = new ArrayList<StudentDetail>();
		for (StudentDetail l : lctList)
		{
			if (l.getFirstName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getLastName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getStudentId().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if ((l.getFirstName().toLowerCase() + " " + l.getLastName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if ((l.getLastName().toLowerCase() + " " + l.getFirstName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getEnrolmentDate().toString().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			}
		}
		
		PagedListHolder<StudentDetail> studentList = null;
		String type = pathVariablesMap.get("type");

		if (null == type) {
			// First Request, Return first set of list
			List<StudentDetail> studentListOther = searchList;
			studentList = new PagedListHolder<StudentDetail>();
			studentList.setSource(studentListOther);
			studentList.setPageSize(10);
			req.getSession().setAttribute("studentListPage", studentList);
		} else {
			// Return specific index set of list
			studentList = (PagedListHolder<StudentDetail>) req.getSession().getAttribute("studentListPage");
			int pageNum = Integer.parseInt(type);
			studentList.setPage(pageNum);
		}
		mav.addObject("dataList", searchList);
		mav.addObject("datacount", searchList.size());
		ModelAndView mv = new ModelAndView("managestudent");
		return mv;
	
	}

//	--------------------------------------------
//	
//	Manage Lecturer Section
//	
//	--------------------------------------------

	//	--------------------------------------------
	// Manage Lecturer Views - All views are processed here, pagination handled here
	//	--------------------------------------------
	@RequestMapping(value = {"/managelecturer/{type}", "/managelecturer"}, method = RequestMethod.GET)
	public ModelAndView manageLecturer(@PathVariable Map<String, String> pathVariablesMap, Locale locale, Model model, @RequestParam Map<String, String> requestParams , HttpServletRequest req)
	{
		try //tries to get ID params, and return 1 result
		{
			// adds list of lecturers to dropdown
			ArrayList<LecturerDetail> lctList = lecturerService.findAllLecturers();
			List<LecturerDetail> tempList = new ArrayList<LecturerDetail>();
			for (LecturerDetail l : lctList)
			{
				if (l.getStatus().toLowerCase().contains("active"))
				{
					tempList.add(l);
				}
			}

			String id = requestParams.get("id");
			ModelAndView mav = new ModelAndView("managelecturer");
			LecturerDetail lecturer = lecturerService.findLecturerById(id);
			tempList.remove(lecturer);
			//adds list of "Open" courses taught by the lecturer
			ArrayList<Course> enrolList = cseService.findbylecid(id);
			ArrayList<Course> enrolList2 = new ArrayList<Course>();
			for (Course e : enrolList)
			{
				if (e.getStatus().contains("Open"))
				{
					enrolList2.add(e);
				}
			}
			mav.addObject("lecturerList", tempList);
			mav.addObject("enroldata", enrolList2);
			mav.addObject("data", lecturer); // add individual lecturer details to MAV
			return mav;
		} catch (Exception e) // catches requestParams.get("id") null pointer exception, lists all lecturers
		{
		
			PagedListHolder<LecturerDetail> lecturerList = null;

			String type = pathVariablesMap.get("type");

			if (null == type) {
				// First Request, Return first set of list
				List<LecturerDetail> lecturerListOther = lecturerService.findAllLecturers();
				lecturerList = new PagedListHolder<LecturerDetail>();
				lecturerList.setSource(lecturerListOther);
				lecturerList.setPageSize(10);
				req.getSession().setAttribute("lecturerListPage", lecturerList);
			} else {
				// Return specific index set of list
				lecturerList = (PagedListHolder<LecturerDetail>) req.getSession().getAttribute("lecturerListPage");
				int pageNum = Integer.parseInt(type);
				lecturerList.setPage(pageNum);
			}

			ModelAndView mv = new ModelAndView("managelecturer");
			return mv;
		}
	}

	//	--------------------------------------------
	// Create Lecturer - Creates User, then Lecturer, and redirects to Manage Lecturer
	//	--------------------------------------------
	@RequestMapping(value = "/createlecturer", method = RequestMethod.POST)
	public String createLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("id");
		if (userService.findUser(id) != null) // Ensures ID is unique before account creation
		{
			return "redirect:managelecturer?actionstatus=userexisterror&id=create";
		}
		else
		{
			String firstName = requestParams.get("firstName");
			String lastName = requestParams.get("lastName");
			String password = requestParams.get("password");
			String role = "Lecturer";
	
			User user = new User(id, password, role);
			userService.createUser(user);
	
			User userTemp = userService.findUser(id);
			id = userTemp.getUserId();
			LecturerDetail lecturer = new LecturerDetail(id, firstName, lastName);
			lecturer.setStatus("Active");
			lecturerService.createLecturer(lecturer);
	
			return "redirect:managelecturer?actionstatus=createsuccess";
		}
	}

	//	--------------------------------------------
	// Update Lecturer + Lecturer User Details, and redirects to Manage Lecturer
	//	--------------------------------------------
	@RequestMapping(value = "/updatelecturer", method = RequestMethod.POST)
	public String updateLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("id");
		String firstName = requestParams.get("firstName");
		String lastName = requestParams.get("lastName");
		String password = requestParams.get("password");
		String role = "Lecturer";

		if (password.length() > 1) // If password length <1, no update
		{
			User user = new User(id, password, role);
			userService.changeUser(user);
		}
		User userTemp = userService.findUser(id);
		id = userTemp.getUserId();

		LecturerDetail lecturer = lecturerService.findLecturerById(id);
		lecturer.setFirstName(firstName);
		lecturer.setLastName(lastName);
		lecturerService.changeLecturer(lecturer);

		return "redirect:managelecturer?actionstatus=success";
	}

	//	--------------------------------------------
	// Soft delete lecturer account and redirects to Manage Student
	//	--------------------------------------------
	@RequestMapping(value = "/deletelecturer", method = RequestMethod.POST)
	public String deleteLecturer(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		String id = requestParams.get("deletethis");
		LecturerDetail lecturer = lecturerService.findLecturerById(id);
		lecturer.setStatus("Disabled");
		lecturerService.changeLecturer(lecturer);
		return "redirect:managelecturer?actionstatus=success";
	}

	//	--------------------------------------------
	// Search Lecturer - Returns full list of lecturer, uses controller to iterate through array
	// (Inefficient, but benefits from not having to write additional queries) - Generates own view
	// Has pagination
	//	--------------------------------------------
	@RequestMapping(value = {"/searchlecturer", "/searchlecturer/{type}"}, method = RequestMethod.GET)
	public ModelAndView searchLecturer(@PathVariable Map<String, String> pathVariablesMap, Locale locale, Model model, @RequestParam Map<String, String> requestParams , HttpServletRequest req)
	{
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		String accountstatus = requestParams.get("accountstatus").toLowerCase();
		if (accountstatus.contains("all"))
		{
			accountstatus = "";
		}
		ModelAndView mav = new ModelAndView("managelecturer");
		ArrayList<LecturerDetail> lctList = lecturerService.findAllLecturers();
		List<LecturerDetail> searchList = new ArrayList<LecturerDetail>();
		for (LecturerDetail l : lctList)
		{
			if (l.getFirstName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getLastName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if (l.getLecturerId().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if ((l.getFirstName().toLowerCase() + " " + l.getLastName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			} else if ((l.getLastName().toLowerCase() + " " + l.getFirstName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(accountstatus))
			{
				searchList.add(l);
			}
		}


		PagedListHolder<LecturerDetail> lecturerList = null;

		String type = pathVariablesMap.get("type");

		if (null == type) {
			// First Request, Return first set of list
			List<LecturerDetail> lecturerListOther = searchList;
			lecturerList = new PagedListHolder<LecturerDetail>();
			lecturerList.setSource(lecturerListOther);
			lecturerList.setPageSize(10);
			req.getSession().setAttribute("lecturerListPage", lecturerList);
		} else {
			// Return specific index set of list
			lecturerList = (PagedListHolder<LecturerDetail>) req.getSession().getAttribute("lecturerListPage");
			int pageNum = Integer.parseInt(type);
			lecturerList.setPage(pageNum);
		}

		ModelAndView mv = new ModelAndView("managelecturer");
		return mv;
		
	}

	//	--------------------------------------------
	// Reassigns course to another lecturer - redirects via string back to Manage Lecturer + profile
	//	--------------------------------------------
	@RequestMapping(value = "/reassignto", method = RequestMethod.GET)
	public String reassignCourseTo(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

		String lecturerId = requestParams.get("lecturerId");
		int courseId = Integer.parseInt(requestParams.get("courseId"));
		String returnTo = requestParams.get("returnTo");

		LecturerDetail lecturer = lecturerService.findLecturerById(lecturerId);
		Course course = cseService.findCourse(courseId);

		course.setLecturerDetails(lecturer);
		cseService.changeCourse(course);

		return "redirect:managelecturer?id=" + returnTo + "&actionstatus=success";
	}

//	--------------------------------------------
//	
//	Manage Course Section
//	
//	--------------------------------------------

	//	--------------------------------------------
	// Manage Course Views - All views are processed here, pagination handled here
	//	--------------------------------------------
	@RequestMapping(value = {"/managecourse","/managecourse/{type}"}, method = RequestMethod.GET)
	public ModelAndView manageCourse(@PathVariable Map<String, String> pathVariablesMap, Locale locale, Model model, @RequestParam Map<String, String> requestParams , HttpServletRequest req)
	{

		// adds list of lecturers to dropdown
		ArrayList<LecturerDetail> lctList = lecturerService.findAllLecturers();
		List<LecturerDetail> tempList = new ArrayList<LecturerDetail>();
		for (LecturerDetail l : lctList)
		{
			if (l.getStatus().toLowerCase().contains("active"))
			{
				tempList.add(l);
			}
		}

		try // returns specific course information if ID is passed in params. Else, null exception thrown
		{
			int id = Integer.parseInt(requestParams.get("id"));
			ModelAndView mav = new ModelAndView("managecourse");
			Course course = cseService.findCourse(id);
			try
			{
				ArrayList<Enrolment> enrolmentList = enrolmentService.findbycid(id);
				mav.addObject("enrolmentList", enrolmentList);
			} catch (Exception e)
			{

			}

			mav.addObject("lecturerList", tempList);
			mav.addObject("data", course);
			return mav;
		} catch (Exception e) // catches null pointer exception and retrieves full list of vourses
		{

			PagedListHolder<Course> courseList = null;

			String type = pathVariablesMap.get("type");

			if (null == type) {
				// First Request, Return first set of list
				List<Course> courseListOther = cseService.findAllCourses();
				courseList = new PagedListHolder<Course>();
				courseList.setSource(courseListOther);
				courseList.setPageSize(5);
				req.getSession().setAttribute("courseListPage", courseList);
			} else {
				// Return specific index set of list
				courseList = (PagedListHolder<Course>) req.getSession().getAttribute("courseListPage");
				int pageNum = Integer.parseInt(type);
				courseList.setPage(pageNum);
			}
			
			ModelAndView mv = new ModelAndView("managecourse");
			mv.addObject("lecturerList", tempList);
			return mv;	
		}
	}

	//	--------------------------------------------
	// Create new course entry - Assigns lecturer. Status 'Open' is hardcoded here.
	// New courses assumed to be 'Open' upon creation
	//	--------------------------------------------
	@RequestMapping(value = "/createcourse", method = RequestMethod.POST)
	public String createCourse(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{

		String cseName = requestParams.get("cseName");
		String lecturerId = requestParams.get("lecturerId");
		int maxCredits = Integer.parseInt(requestParams.get("maxCredits"));
		Date startDate = ConvertToDate(requestParams.get("startDate"));
		Date endDate = ConvertToDate(requestParams.get("endDate"));
		int size = Integer.parseInt(requestParams.get("size"));
		
		//ensures  start date is > end date, and that the revised max size of course is > current enrolment
		if (endDate.compareTo(startDate) > 0 && size >0)
		{
			String status = "Open";
			int currentEnrollment = 0;
			LecturerDetail lecturer = lecturerService.findLecturerById(lecturerId);
	
			Course course = new Course();
			course.setCourseName(cseName);
			course.setSize(size);
			course.setStatus(status);
			course.setStartDate(startDate);
			course.setEndDate(endDate);
			course.setLecturerDetails(lecturer);
			course.setCurrentEnrollment(currentEnrollment);
			course.setCredits(maxCredits);
	
			cseService.changeCourse(course);
			return "redirect:managecourse?actionstatus=createsuccess";
		}
		else
		{
			return "redirect:managecourse?actionstatus=failcuzofdate&id=create";
		}
	}

	//	--------------------------------------------
	// Update Course information
	//	--------------------------------------------
	@RequestMapping(value = "/updatecourse", method = RequestMethod.POST)
	public String updateCourse(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("id"));
		String cseName = requestParams.get("cseName");
		int size = Integer.parseInt(requestParams.get("size"));		
		String status = requestParams.get("status");
		String lecturerId = requestParams.get("lecturerId");
		LecturerDetail lecturer = lecturerService.findLecturerById(lecturerId);
		Date startDate = ConvertToDate(requestParams.get("startDate"));
		Date endDate = ConvertToDate(requestParams.get("endDate"));
		int maxCredits = Integer.parseInt(requestParams.get("maxCredits"));
		

			
		Course course = cseService.findCourse(id);
		
		//ensures  start date is > end date, and that the revised max size of course is > current enrolment
		if (endDate.compareTo(startDate) > 0 && size > course.getCurrentEnrollment())
		{
		course.setCourseName(cseName);
		course.setSize(size);
		course.setStatus(status);
		course.setLecturerDetails(lecturer);
		course.setStartDate(startDate);
		course.setEndDate(endDate);
		course.setCredits(maxCredits);
		
		cseService.changeCourse(course);
		return "redirect:managecourse?actionstatus=success";
		}
		else
		{
			return "redirect:managecourse?actionstatus=failcuzofdate&id="+id;
		}
	}

	//	--------------------------------------------
	// Soft-delete course - Course status is set to 'Closed', and user is redirected to Manage Course
	//	--------------------------------------------
	@RequestMapping(value = "/deletecourse", method = RequestMethod.POST)
	public String deleteCourse(Locale locale, Model model, @RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("deletethis"));
		Course course = cseService.findCourse(id);
		course.setStatus("Closed");
		cseService.changeCourse(course);
		return "redirect:managecourse?actionstatus=success";
	}

	//	--------------------------------------------
	// Search Course - Returns full list of lecturer, uses controller to iterate through array
	// (Inefficient, but benefits from not having to write additional queries) - Generates own view
	// Has pagination
	//	--------------------------------------------
	@RequestMapping(value = {"/searchcourse","/searchcourse/{type}"}, method = RequestMethod.GET)
	public ModelAndView searchCourse(@PathVariable Map<String, String> pathVariablesMap, Locale locale, Model model, @RequestParam Map<String, String> requestParams , HttpServletRequest req)
	{
		String searchContent = requestParams.get("searchcontent").toLowerCase();
		String coursestatus = requestParams.get("accountstatus").toLowerCase();
		if (coursestatus.contains("all"))
		{
			coursestatus = "";
		}
		ModelAndView mav = new ModelAndView("managecourse");
		ArrayList<Course> lctList = cseService.findAllCourses();
		List<Course> searchList = new ArrayList<Course>();
		for (Course l : lctList)
		{
			if (Integer.toString(l.getCourseId()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if (l.getCourseName().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if (l.getLecturerDetails().getLecturerId().toLowerCase().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if ((l.getLecturerDetails().getFirstName().toLowerCase() + " "
					+ l.getLecturerDetails().getLastName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if ((l.getLecturerDetails().getLastName().toLowerCase() + " "
					+ l.getLecturerDetails().getFirstName().toLowerCase()).contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if (l.getStartDate().toString().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			} else if (l.getEndDate().toString().contains(searchContent)
					&& l.getStatus().toLowerCase().contains(coursestatus))
			{
				searchList.add(l);
			}
		}

		PagedListHolder<Course> courseList = null;
		String type = pathVariablesMap.get("type");
		if (null == type) {
			// First Request, Return first set of list
			List<Course> courseListOther = searchList;;
			courseList = new PagedListHolder<Course>();
			courseList.setSource(courseListOther);
			courseList.setPageSize(5);
			req.getSession().setAttribute("courseListPage", courseList);
		} else {
			// Return specific index set of list
			courseList = (PagedListHolder<Course>) req.getSession().getAttribute("courseListPage");
			int pageNum = Integer.parseInt(type);
			courseList.setPage(pageNum);
		}
		ModelAndView mv = new ModelAndView("managecourse");
		return mv;
	}

	//	--------------------------------------------
	// Removes student from a course - Sets Enrolment Status to 'Removed', grade to 'N/A'
	//	--------------------------------------------
	@RequestMapping(value = "/removestudentenrolmentviacourse", method = RequestMethod.POST)
	public String removeStudentFromEnrolmentViaCourse(Locale locale, Model model,
			@RequestParam Map<String, String> requestParams)
	{
		int id = Integer.parseInt(requestParams.get("removethis"));
		String courseId = requestParams.get("removethisbyId");
		Enrolment enrolment = enrolmentService.findbyEnrolmentId(id);
		enrolment.setStatus("Removed");
		enrolment.setGrade("N/A");

		enrolmentService.updateEnrolment(enrolment);
		AddCourseEnrolmentCounter(enrolment.getCourses(), false);

		return "redirect:managecourse?actionstatus=success&id=" + courseId;
	}

//	--------------------------------------------
//	
//	Miscellaneous methods
//	
//	--------------------------------------------
	
	
	//	--------------------------------------------
	// To get date formatted from string (year -1900, & for some reason, month-1 is required as well
	// Utilizes deprecated date constructor
	//	--------------------------------------------
	@SuppressWarnings("deprecation")
	private static Date ConvertToDate(String dateString)
	{
		List<Integer> dateArray = new ArrayList<Integer>();

		for (String s : dateString.split("-"))
		{
			dateArray.add(Integer.parseInt(s));
		}
		Date date = new Date(dateArray.get(0) - 1900, dateArray.get(1)-1, dateArray.get(2));
		return date;
	}

	//	--------------------------------------------
	// Adds/Deducts from course current enrolment counter. Bool 'true' to add, 'false' to deduct
	//	--------------------------------------------
	private void AddCourseEnrolmentCounter(Course c, Boolean b)
	{
		if (b)
		{
			c.setCurrentEnrollment(c.getCurrentEnrollment() + 1);
			cseService.changeCourse(c);
		} else
		{
			c.setCurrentEnrollment(c.getCurrentEnrollment() - 1);
			cseService.changeCourse(c);
		}
	}

}
