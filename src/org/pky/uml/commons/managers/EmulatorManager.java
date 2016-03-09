package org.pky.uml.commons.managers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.pky.uml.model.AndroidDeviceModel;

public class EmulatorManager {
	private static EmulatorManager instance;

	private String androidNewDeviceName = "";
	public static EmulatorManager getInstance() {
		if (instance == null) {
			instance = new EmulatorManager();


			return instance;
		}
		else {
			return instance;
		}
	}



	//AVDManager 실행 
	public void openAVDManager(){
		ProjectManager.getInstance().commanedExe(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_ANDROID+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_UN_AVD);
	}
	public ArrayList<AndroidDeviceModel> getAndroidDevices(){

		ArrayList list = new ArrayList();

		ArrayList<String> commedLineList = ProjectManager.getInstance().commend(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_PLATFORM_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_ADB+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_DEVICES);

		AndroidDeviceModel androidDeviceModel = null;
		for(int i = 1 ; i <  commedLineList.size(); i ++){
			System.out.println(commedLineList.get(i));

			if(!commedLineList.get(i).trim().equals("")){
				androidDeviceModel = new AndroidDeviceModel();
				if(commedLineList.get(i).indexOf("\t")>0){
					androidDeviceModel.setAvdName(commedLineList.get(i).substring(0, commedLineList.get(i).indexOf("\t")));
					list.add(androidDeviceModel);
				}
			}
		}
		return list;

	}
	//AVD정보 파싱해서 ArrayList로 반환 
	public ArrayList<AndroidDeviceModel> getAndroidEmulators(){
		ArrayList list = new ArrayList();

		ArrayList<String> commedLineList = ProjectManager.getInstance().commend(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_ANDROID+LayoutManager.SPACE_TOKEN + LayoutManager.CMD_LIST+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_UN_AVD);

		AndroidDeviceModel androidDeviceModel = null;
		for(int i = 0 ; i <  commedLineList.size(); i ++){


			if(commedLineList.get(i).trim().startsWith("Name:")){
				androidDeviceModel = new AndroidDeviceModel();
				androidDeviceModel.setAvdName(commedLineList.get(i).substring(commedLineList.get(i).lastIndexOf(":")+1, commedLineList.get(i).length()).trim());
			}else if(commedLineList.get(i).trim().startsWith("Target:")){
				androidDeviceModel.setSdkVer(commedLineList.get(i).substring(commedLineList.get(i).lastIndexOf(":")+1, commedLineList.get(i).length()).trim());
				list.add(androidDeviceModel);
			}
		}
		return list;
	}


	//에뮬레이터 실행 
	public void openAndroidEmulator(String avd,final String path){
		ProgressMonitorDialog pd = new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());

		ProjectManager.getInstance().commanedExe(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_EMULATOR+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_WIPE_DATA+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_AVD+LayoutManager.SPACE_TOKEN+ avd);

		final String avdName = "";
		IRunnableWithProgress iRunnableWithProgress = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
				monitor.setTaskName("Emulator구동 대기중..");
				//현재 구동중인 Device검색 
				
				ArrayList<AndroidDeviceModel> list = EmulatorManager.getInstance().getAndroidDevices();				
				HashMap<String,Boolean> map = new HashMap();
				//해당 내용 map에 넣어둔다. 
				for(int i = 0 ; i < list.size(); i ++){
					map.put(list.get(i).getAvdName(),true);
				}
				if(path!=null && !path.equals("")){
					if(monitor.isCanceled()){
						return;
					}
					try{Thread.sleep(35000);}catch (Exception e) {}
					list = EmulatorManager.getInstance().getAndroidDevices();

					for(int i = 0 ; i < list.size(); i ++){
						if(map.get(list.get(i).getAvdName())==null){
							EmulatorManager.getInstance().setAndroidNewDeviceName(list.get(i).getAvdName());
						}
					}
				}


			}
		};
		try{
			pd.run(true, true, iRunnableWithProgress);
			EmulatorManager.getInstance().androidAppInstall(androidNewDeviceName, path);
			EmulatorManager.getInstance().setAndroidNewDeviceName(null);

		}catch(Exception e) {
			e.printStackTrace();
		}


	}
	//앱 실행
	public void openAndroidApp(String avdName,String packageName,String activityName){


		ProjectManager.getInstance().commanedExe(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_PLATFORM_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_ADB+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_S_L+LayoutManager.SPACE_TOKEN+avdName+LayoutManager.SPACE_TOKEN+ LayoutManager.CMD_SHELL + LayoutManager.SPACE_TOKEN + LayoutManager.CMD_AM + LayoutManager.SPACE_TOKEN + LayoutManager.CMD_START + LayoutManager.SPACE_TOKEN + LayoutManager.CMD_N_L + LayoutManager.SPACE_TOKEN+packageName + LayoutManager.SEPARATOR  + activityName);

	}
	//앱 설치 
	public void androidAppInstall(String avdName1,String path1){

		ProgressMonitorDialog pd = new ProgressMonitorDialog(ProjectManager.getInstance().window.getShell());


		final String avdName = avdName1;
		final String path = path1;

		IRunnableWithProgress iRunnableWithProgress = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {


				try {

					boolean isOnline = true;
					int count = 0;
					while(isOnline){
						count ++;
						
						if(monitor.isCanceled()){
							return;
						}

						monitor.setTaskName("Device검색중..");
						ArrayList<String> list = ProjectManager.getInstance().commend(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_PLATFORM_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_ADB+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_DEVICES);
						System.out.println(list);
						for(int i = 0 ; i < list.size(); i ++){
							if(list.get(i).startsWith(avdName)&& list.get(i).endsWith("device")){
								isOnline = false;
								break;
							}
						}
						if(isOnline)
							Thread.sleep(45);

						if(count > 300)
							break;



					}


					monitor.setTaskName("APK 유효성 체크중..");
					ArrayList<String> commendList = ProjectManager.getInstance().commend(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_PLATFORM_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_AAPT+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_DUMP+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_BADGING+LayoutManager.SPACE_TOKEN+path);

					String apkPackage = "";
					String launchableActivity = "";
					for(int i = 0; i < commendList.size(); i ++){
						String line = commendList.get(i);
						if(line.startsWith("package: name='") && line.indexOf("' versionCode=")>=0){

							apkPackage = line.substring(line.indexOf("'")+1,line.indexOf("' versionCode="));
						}else  if(line.startsWith("launchable-activity: name='") && line.indexOf("'  label=")>=0){
							launchableActivity = line.substring(line.indexOf("'")+1,line.indexOf("'  label="));
						}else if(line.startsWith("ERROR:")){
							ProjectManager.getInstance().showMessage(MessageDialog.ERROR, "에러", "정상적인 APK 파일이 아닙니다.");
							return;
						}
					}
					count = 0;
					boolean isLoad = true;
					while(isLoad){
						monitor.setTaskName("Device 상태 대기중..");

						if (monitor.isCanceled())
							return;

						commendList = ProjectManager.getInstance().commend(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_PLATFORM_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_ADB+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_S_L+LayoutManager.SPACE_TOKEN+avdName+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_INSTALL+LayoutManager.SPACE_TOKEN+path);
						Thread.sleep(30);
						for(int i = 0; i < commendList.size(); i ++){
							System.out.println(count);




							if(commendList.get(i).startsWith("Success")){
								monitor.setTaskName("Device 파일 전송 및 설치 중..");
								openAndroidApp(avdName,apkPackage,launchableActivity);
								isLoad = false;
							}else if(commendList.get(i).indexOf("[INSTALL_FAILED_OLDER_SDK]")>=0){
								ProjectManager.getInstance().showMessage(MessageDialog.ERROR, "실패", "파일전송에 실패 하였습니다. 관리자에게 문의 하시기 바랍니다."+LayoutManager.ENTER_TOKEN+"pky1030@nate.com");
								isLoad = false;
								break;
							}else if(commendList.get(i).indexOf("INSTALL_FAILED")>=0 ){
								monitor.setTaskName("기존 App실행 중지 및 삭제 중..");
								ProjectManager.getInstance().commanedExe(ProjectManager.getInstance().getAndroidSDKPath()+LayoutManager.SEPARATOR+LayoutManager.ANDROID_PLATFORM_TOOLS_PATH+LayoutManager.SEPARATOR+LayoutManager.ANDROID_CMD_ADB+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_S_L+LayoutManager.SPACE_TOKEN+avdName+LayoutManager.SPACE_TOKEN+LayoutManager.CMD_UNINSTALL+LayoutManager.SPACE_TOKEN+apkPackage);
							}
							count ++;


						}
						if(count>200)
							isLoad = false;

					}



				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		try{
			pd.run(true, true, iRunnableWithProgress);

		}catch(Exception e) {
			e.printStackTrace();
		}


	}



	public String getAndroidNewDeviceName() {
		return androidNewDeviceName;
	}



	public void setAndroidNewDeviceName(String androidNewDeviceName) {
		this.androidNewDeviceName = androidNewDeviceName;
	}




}
