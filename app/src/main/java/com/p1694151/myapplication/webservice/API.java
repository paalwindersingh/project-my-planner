package com.p1694151.myapplication.webservice;



import com.p1694151.myapplication.models.GeneralResponse;
import com.p1694151.myapplication.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.p1694151.myapplication.webservice.Constants.ADD_EVENT;
import static com.p1694151.myapplication.webservice.Constants.ADD_TASK_TODO_LIST;
import static com.p1694151.myapplication.webservice.Constants.CHANGE_PASSWORD;
import static com.p1694151.myapplication.webservice.Constants.CHANGE_USERNAME;
import static com.p1694151.myapplication.webservice.Constants.DELETE_EVENT;
import static com.p1694151.myapplication.webservice.Constants.DELETE_TODO_ITEM;
import static com.p1694151.myapplication.webservice.Constants.DELETE_TODO_LIST_ITEM;
import static com.p1694151.myapplication.webservice.Constants.EDIT_TODO_LIST;
import static com.p1694151.myapplication.webservice.Constants.EVENT;
import static com.p1694151.myapplication.webservice.Constants.EVENT_LIST;
import static com.p1694151.myapplication.webservice.Constants.EVENT_LOCATION;
import static com.p1694151.myapplication.webservice.Constants.EVENT_REMINDER;
import static com.p1694151.myapplication.webservice.Constants.SHARED_EVENT_BY_USER;
import static com.p1694151.myapplication.webservice.Constants.SHARED_EVENT_WITH_USER;
import static com.p1694151.myapplication.webservice.Constants.SIGN_IN;
import static com.p1694151.myapplication.webservice.Constants.SIGN_UP;
import static com.p1694151.myapplication.webservice.Constants.TODO_LIST_ITEM;
import static com.p1694151.myapplication.webservice.Constants.TODO_LIST;
import static com.p1694151.myapplication.webservice.Constants.UPDATE_EVENT;

public interface API {

    @GET(SIGN_IN)
    Call<User> signin(@Query("email") String email, @Query("password") String password);

    @GET(SIGN_UP)
    Call<GeneralResponse> signup(@Query("firstname") String firstname,@Query("lastname") String lastname,@Query("dob") String dob,@Query("gender") String gender,@Query("email") String email,@Query("password") String password,@Query("phone") String phone);

    @GET(EVENT)
    Call<GeneralResponse> getEvent(@Query("eventid") String eventid);

    @GET(EVENT_LIST)
    Call<GeneralResponse> getEventList();

    @GET(TODO_LIST)
    Call<GeneralResponse> getTodoList(@Query("userId") String userId);

    @GET(TODO_LIST_ITEM)
    Call<GeneralResponse> getTodoListItem(@Query("todoListId") String todoListId);

    @GET(DELETE_TODO_LIST_ITEM)
    Call<GeneralResponse> deleteTodoListItem(@Query("todoListId") String todoListId);

    @GET(DELETE_EVENT)
    Call<GeneralResponse> deleteEvent(@Query("eventId") String eventId);

    @GET(DELETE_TODO_ITEM)
    Call<GeneralResponse> deleteTodoItem(@Query("todoListId") String todoListId,@Query("taskId") String taskId);

    @GET(ADD_EVENT)
    Call<GeneralResponse> addEvent(@Query("Event_Title") String Event_Title,@Query("Start_Time") String Start_Time,@Query("End_Time") String End_Time,@Query("Start_Date") String Start_Date,
                                   @Query("End_Date") String End_Date,@Query("Description") String Description,@Query("Address") String Address,@Query("City") String City,
                                   @Query("State") String State,@Query("Postal_Code") String Postal_Code,@Query("Reminder_Start_Time") String Reminder_Start_Time,@Query("Reminder_End_Time") String Reminder_End_Time,
                                   @Query("Reminder_Date") String Reminder_Date,@Query("Reminder_Description") String Reminder_Description);
    @GET(UPDATE_EVENT)
    Call<GeneralResponse> updateEvent(@Query("Event_Title") String Event_Title,@Query("Start_Time") String Start_Time,@Query("End_Time") String End_Time,@Query("Start_Date") String Start_Date,
                                   @Query("End_Date") String End_Date,@Query("Description") String Description,@Query("Address") String Address,@Query("City") String City,
                                   @Query("State") String State,@Query("Postal_Code") String Postal_Code,@Query("Reminder_Start_Time") String Reminder_Start_Time,@Query("Reminder_End_Time") String Reminder_End_Time,
                                   @Query("Reminder_Date") String Reminder_Date,@Query("Reminder_Description") String Reminder_Description);

    @GET(EDIT_TODO_LIST)
    Call<GeneralResponse> editTodoList(@Query("Id_todolist") String Id_todolist,@Query("Id_Task") String Id_Task,@Query("notes") String notes);

    @GET(ADD_TASK_TODO_LIST)
    Call<GeneralResponse> addTodoList(@Query("Title_todolist") String Title_todolist,@Query("Title_Task") String Title_Task,@Query("notes") String notes);

    @GET(SHARED_EVENT_BY_USER)
    Call<GeneralResponse> sharedEventByUser(@Query("user_Id") String user_Id);

    @GET(SHARED_EVENT_WITH_USER)
    Call<GeneralResponse> sharedEventWithUser(@Query("user_Id") String user_Id);

    @GET(EVENT_REMINDER)
    Call<GeneralResponse> eventReminder(@Query("eventId") String eventId);

    @GET(EVENT_LOCATION)
    Call<GeneralResponse> eventLocation(@Query("eventID") String eventID);

    @GET(CHANGE_PASSWORD)
    Call<GeneralResponse> changePassword(@Query("Email_Id") String Email_Id,@Query("Old_Password") String Old_Password,@Query("New_Password") String New_Password);

    @GET(CHANGE_USERNAME)
    Call<GeneralResponse> changeUsername(@Query("Email_Id") String Email_Id,@Query("firstname") String firstname,@Query("lastname") String lastname);
}