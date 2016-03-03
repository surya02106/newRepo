package org.com.services;

import java.util.*;

import org.apache.log4j.Logger;
import org.com.dao.BaseDao;
import org.com.model.Friends;
import org.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"rawtypes","unchecked"})
public class FriendsDetailsImpl implements FriendsDetails {
	
	private static final Logger logger = Logger.getLogger(FriendsDetailsImpl.class);
	
	@Autowired
	BaseDao baseDao;
	
	@Autowired
	MessageStorage msgStore;

	@Override
	public List getFriendsList(User user) {
		logger.info("Query for friends list");	
		ArrayList arr=new ArrayList();
		List<Object[]> list=baseDao.setQuery("select u.id, u.firstname, u.lastname, f.accepted from User u, Friends f where ((f.meid="+user.getId()+") and (u.id=f.youid)) OR ((f.youid="+user.getId()+") AND (u.id=f.meid) AND f.accepted='1')");
		for(int i=0;i<list.size();i++){
			User u=new User();
			Object obj[]=list.get(i);
			u.setId((int) obj[0]);
			u.setFirstname((String) obj[1]);
			u.setLastname((String) obj[2]);
			u.setFlag((String)obj[3]);
			msgStore.getloginInHM(u);
			arr.add(u);
		}
		return arr;
	}

	@Override
	public List getUsersList(String[] key) {
		logger.info("Query for Searching friends list");		
		List list = baseDao.setQuery(key[0]);
		List list1 = baseDao.setQuery("From Friends where (youid='"+key[1]+"' or meid='"+key[1]+"')");
		Iterator itr=list1.iterator();
		ArrayList arr=new ArrayList();
		while(itr.hasNext()){
			Friends frnd=(Friends)itr.next();
			if(!frnd.getYouid().equals(key[1])){
				arr.add(Integer.parseInt(frnd.getYouid()));
			}else if(!frnd.getMeid().equals(key[1])){
				arr.add(Integer.parseInt(frnd.getMeid()));
			}
		}
		ArrayList arr1=new ArrayList();
		itr=list.iterator();
		while(itr.hasNext()){
			User user=(User)itr.next();
			if(arr.contains(user.getId()))
				continue;
			arr1.add(user);
		}
		return arr1;
	}
	
	@Override
	public User getUserDetails(int id) {
		logger.info("Query for Searched friend details");
		return (User)baseDao.getEntityById(User.class, id);
	}
	
	@Override
	public String setFriendRequest(Friends friends) {
		logger.info("Query for Setting Friend Request");
		baseDao.saveOrUpdate(friends);
		return "000";
	}
	
	@Override
	public int getFriendRequest(User user) {
		logger.info("Query for Searched friends Request");
		return baseDao.getUniqueValues("select count(*) from Friends where youid='"+user.getId()+"' AND accepted='0'");
	}

	@Override
	public List getFriendRequestList(User user) {
		logger.info("Query for friends list Request");	
		ArrayList arr=new ArrayList();
		List<Object[]> list=baseDao.setQuery("select u.id, u.firstname, u.lastname, u.email from User u, Friends f where (f.youid="+user.getId()+" AND f.accepted='0') and (u.id=f.meid)");
		for(int i=0;i<list.size();i++){
			User u=new User();
			Object obj[]=list.get(i);
			u.setId((int) obj[0]);
			u.setFirstname((String) obj[1]);
			u.setLastname((String) obj[2]);
			u.setEmail((String) obj[3]);
			arr.add(u);
		}
		return arr;
	}
	
	@Override
	public boolean setFriendRequestAccepted(int[] arr){
		return baseDao.saveOrupdateByQuery("update Friends set accepted='1' where youid='"+arr[1]+"' AND meid='"+arr[0]+"'");
	}
}
