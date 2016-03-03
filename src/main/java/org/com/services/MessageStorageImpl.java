package org.com.services;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;
import org.com.dao.BaseDao;
import org.com.model.AllMsg;
import org.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"unchecked","rawtypes"})
public class MessageStorageImpl implements MessageStorage {
	
	private static final Logger logger = Logger.getLogger(MessageStorageImpl.class);
	
	@Autowired
	BaseDao baseDao;
	
	public static HashMap<Integer,ArrayList> hm = new HashMap<Integer,ArrayList>();
	
	@Override
	public void setloginInHM(User user){
		logger.info("User login set to HashMap : "+user.toString());
		hm.put(user.getId(),null);
	}
	
	@Override
	public String setLogoutInHM(User user){
		logger.info("User Logout from HashMap : "+user.toString());
		try{
			ArrayList ar=hm.get(user.getId());
			if(ar!=null){
				Iterator itr=ar.iterator();
				logger.info("All messages to database");
				while(itr.hasNext()){
					AllMsg msg=(AllMsg)itr.next();
					baseDao.saveOrUpdate(msg);
				}
			}
			hm.remove(user.getId());
			return "000";
		}catch(Exception e){
			logger.info("Error while logout for user : "+user.toString());
			logger.error("Exception : "+e.getMessage());
			return "111";
		}
	}
	
	@Override
	public User getloginInHM(User user){
		logger.info("Check for User live : "+user.toString());
		if(hm.containsKey(user.getId())){
			user.setStatus("1");
		}
		logger.info("After setting flag : "+user.toString());
		return user;
	}
	
	@Override
	public AllMsg setMsgsToHM(AllMsg message){
		logger.info("Message set to DateBase : "+message.toString());
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy hh:mm:ss");
		message.setTimedate(ft.format(date));
		baseDao.saveOrUpdate(message);
		logger.info(hm+"  "+message.getTomsg());
		if(hm.containsKey(Integer.parseInt(message.getTomsg()))){
			if(hm.get(Integer.parseInt(message.getTomsg()))==null){
				ArrayList arr=new ArrayList();
				logger.info("New ArrayList Created : "+hm+"  "+message.getTomsg());
				arr.add(message);
				hm.put(Integer.parseInt(message.getTomsg()), arr);
			}else{
				ArrayList arr=hm.get(Integer.parseInt(message.getTomsg()));
				arr.add(message);
				logger.info("Old ArrayList used : "+hm+"  "+message.getTomsg());
			}			
		}
		return message;
	}
	
	@Override
	public List getAllMsgFromDB(User user){
		return baseDao.setQuery("From AllMsg where tomsg='"+user.getId()+"' AND flag='0'");
	}
	
	@Override
	public List getAllMsgFromDBForSpecificUser(int[] arr){
		logger.info("updating flag of Messages from user with id : "+arr[1]+" For user with id : "+arr[0]);
		baseDao.saveOrupdateByQuery("update AllMsg set flag='1' where tomsg='"+arr[0]+"' AND frommsg='"+arr[1]+"'");
		logger.info("Getting Messages from specific user with id : "+arr[1]+" For user with id : "+arr[0]);
		ArrayList ar=hm.get(arr[0]);
		if(ar!=null){
			Iterator itr=ar.iterator();
			while(itr.hasNext()){
				AllMsg msg=(AllMsg)itr.next();
				if(msg.getFrommsg().equals(Integer.toString(arr[1]))){
					msg.setFlag("1");					
				}
			}
		}	
		return baseDao.setQuery("From AllMsg where (tomsg='"+arr[0]+"' AND frommsg='"+arr[1]+"') or (tomsg='"+arr[1]+"' AND frommsg='"+arr[0]+"')");
	}
	
	@Override
	public List getMsgFromHM(int[] arr) {
		
		ArrayList arrayList=hm.get(arr[0]);
		ArrayList arrR=new ArrayList();
		logger.info("Message List "+arrayList);
		boolean flag=true;
		if(arrayList!=null){
			Iterator itr=arrayList.iterator();
			while(itr.hasNext()){
				AllMsg msg=(AllMsg)itr.next();
				if(msg.getStatus().equals("0")){
					for(int i=1;i<arr.length;i++){
						if(msg.getFrommsg().equals(Integer.toString(arr[i]))){
							msg.setFlag("1");
							msg.setStatus("1");
							arrR.add(msg);
							flag=false;
						}
					}
					if(flag==true){
						System.out.println("lllllll");
						msg.setStatus("1");
						arrR.add(msg);
						flag=true;
					}					
				}
			}
		}
		logger.info("Return Message ArrayList : "+arrR);
		return arrR;
	}
	
	@Override
	public Integer[] getActiveFriendsList(){
		logger.info("Collecting List of Active users");
		Set<Integer> keys = hm.keySet();
		Integer[] array = keys.toArray(new Integer[keys.size()]);
		logger.info("Returned array of userids of active user : "+array);
		return array;
	}
}
