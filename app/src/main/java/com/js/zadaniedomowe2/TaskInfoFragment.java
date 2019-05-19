package com.js.zadaniedomowe2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.js.zadaniedomowe2.tasks.TaskListContent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class TaskInfoFragment extends Fragment implements View.OnClickListener{

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    private TaskListContent.Task mDsiplayedTask;

    public TaskInfoFragment() {

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        FragmentActivity activity = getActivity ();
        super.onActivityCreated ( savedInstanceState );
        activity.findViewById(R.id.displayFragment).setVisibility(View.INVISIBLE);
        activity.findViewById(R.id.taskInfoImage).setOnClickListener(this);
        Intent intent = getActivity ().getIntent ();
        if(intent!=null)
        {
            TaskListContent.Task receivedTask = intent.getParcelableExtra ( MainActivity.taskExtra );
            if(receivedTask!= null)
            {
                displayTask ( receivedTask );
            }
        }
    }
//(The onActivityCreated method is called when the Activity
//that contains the Fragment is ready
    public void displayTask(TaskListContent.Task task)
    {
        FragmentActivity activity = getActivity ();
        (activity.findViewById ( R.id.displayFragment )).setVisibility ( View.VISIBLE );
        TextView taskInfoTitle = activity.findViewById ( R.id.taskInfoTitle );
        TextView taskInfoDescription = activity.findViewById ( R.id.taskInfoDescription );
        final ImageView taskInfoImage = activity.findViewById ( R.id.taskInfoImage );


        taskInfoTitle.setText ( task.title );
        taskInfoDescription.setText ( "DATA WYDARZENIA: "+ task.data + "\n\nOPIS: " + task.details);
        if(task.picPath!=null && !task.picPath.isEmpty ())
        {
            if(task.picPath.contains ( "drawable" ))
            {
                Drawable taskDrawable;
                switch (task.picPath)
                {
                    case "drawable 1":
                        taskDrawable = activity.getResources ().getDrawable ( R.drawable.circle_drawable_green );
                        break;
                    case "drawable 2":
                        taskDrawable = activity.getResources ().getDrawable ( R.drawable.circle_drawable_orange );
                        break;
                    case "drawable 3":
                        taskDrawable = activity.getResources ().getDrawable ( R.drawable.circle_drawable_red );
                        break;
                    default:
                        taskDrawable = activity.getResources ().getDrawable ( R.drawable.circle_drawable_green );
                }
                taskInfoImage.setImageDrawable ( taskDrawable );
            }
            else
            {
                Handler handler = new Handler (  );
                taskInfoImage.setVisibility ( View.INVISIBLE );
                handler.postDelayed ( new Runnable () {
                    @Override
                    public void run() {
                        taskInfoImage.setVisibility ( View.VISIBLE );
                        Bitmap cameraImage = PicUtils.decodePic ( mDsiplayedTask.picPath, taskInfoImage.getWidth (), taskInfoImage.getHeight () );
                        taskInfoImage.setImageBitmap ( cameraImage );
                    }
                }, 200 );
            }
        }
        else
        {
            taskInfoImage.setImageDrawable ( activity.getResources ().getDrawable ( R.drawable.circle_drawable_green ) );
        }
        mDsiplayedTask = task;
    }
    //this method is used to display the details of the task.  In this fragment of the code a Handler class is used to delay
    //(handler.postDelayed()) the process of displaying the picture.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate ( R.layout.fragment_task_info, container, false );
    }

    @Override
    public void onClick(View v) {
        Intent takePictureIntent = new Intent ( MediaStore.ACTION_IMAGE_CAPTURE );
        if(takePictureIntent.resolveActivity ( Objects.requireNonNull ( getActivity () ).getPackageManager () ) != null){
            File photoFile = null;
            try{
                photoFile = createImageFile ();
            }
            catch (IOException ignored){}
            if(photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile ( getActivity (), getString ( R.string.myFileprovider ), photoFile );
                takePictureIntent.putExtra ( MediaStore.EXTRA_OUTPUT, photoURI );
                startActivityForResult ( takePictureIntent, REQUEST_IMAGE_CAPTURE );
            }
        }
    }
    //This code enables the user to access the camera with the
    //MediaStore.ACTION_IMAGE_CAPTURE intent

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat ( "yyyyMMdd_HHmmss" ).format ( new Date (  ) );
        String imageFileName = mDsiplayedTask.title + timeStamp + "_";
        File storageDir = getActivity ().getExternalFilesDir ( Environment.DIRECTORY_PICTURES );
        File image = File.createTempFile ( imageFileName, ".jpg", storageDir );
        mCurrentPhotoPath = image.getAbsolutePath ();
        return image;
    }
//This method creates a file used for storing the photo taken with the camera. It also saves the file path
//in the mCurrentPhotoPath variable. The file is saved in the External’s storage directory specified
//for holding pictures. A timeStamp is used to set an unique name for the file.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            FragmentActivity holdingActivity = getActivity ();
            if(holdingActivity != null)
            {
                ImageView taskImage = holdingActivity.findViewById ( R.id.taskInfoImage );
                Bitmap cameraImage = PicUtils.decodePic ( mCurrentPhotoPath, taskImage.getWidth (), taskImage.getHeight () );
                taskImage.setImageBitmap ( cameraImage );
                mDsiplayedTask.setPicPath ( mCurrentPhotoPath );
                TaskListContent.Task task = TaskListContent.ITEM_MAP.get ( mDsiplayedTask.id );
                if(task != null)
                {
                    task.setPicPath ( mCurrentPhotoPath );
                }

                if(holdingActivity instanceof MainActivity)
                {
                    ((TaskFragment) holdingActivity.getSupportFragmentManager ().findFragmentById ( R.id.taskFragment )).notifyDataChange ();
                }
                else if (holdingActivity instanceof TaskInfoActivity)
                {
                    (( TaskInfoActivity ) holdingActivity).setImgChanged ( true );
                }
            }
        }
    }
}
//In this method the application responds to the result returned by the
//MediaStore.ACTION_IMAGE_CAPTURE intent. If the returned result is RESULT_OK the taken image
//is decoded by a decodePic method of the PicUtils and displayed in the taskInfoImage
//ImageView.