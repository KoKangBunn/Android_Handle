package com.example.asus.android_handle;

import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textView;
    private ImageView imageView;
    private Button button;
    public int age;
    public String name ;

    @Override
    public void onClick(View v) {
     //   handler.removeCallbacks(myRunnable);
        handler.sendEmptyMessage(1);
    }

    class Person {
        public int age;
        public String name;

        public String toString(){

            return "name="+name + " age=" +age;
        }
    }
    // callback对象可以截获handle对象发过来的消息，
    // 当返回值设置的是ture 的情况下的时候，第二个方法就不会执行了
    // 起到消息拦截的作用
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(),""+1 ,Toast.LENGTH_SHORT).show();
            return false;
        };
    }){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(),""+2 ,Toast.LENGTH_SHORT).show();
        };
    };
    private  int Images[] = {R.drawable.aa,R.drawable.bb,R.drawable.cc};
    private int index;
    // new 出一个Myrunnable的类，在run中每隔一秒钟，执行一次myrunnable的类方法、
    private MyRunnable myRunnable = new MyRunnable();
       class MyRunnable implements  Runnable{
        @Override
        public void run() {
            index++;
            index = index%3;
            imageView.setImageResource(Images[index]);
            //每隔一秒钟执行一次runnable的方法
           handler.postDelayed(myRunnable,1000);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);
        imageView = findViewById(R.id.image);
        button = findViewById(R.id.button1);
        button.setOnClickListener(this);

    //    handler.postDelayed(myRunnable,1000);

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    //Message message = new Message();
                    //message.arg1 =  88;
                    Message message = handler.obtainMessage();
                    Person person = new Person();
                    person.age = 100;
                    person.name = "guhanwen";
                    message.obj =  person;


                    // 由这个handle发送到new 的Handle的方法当中，而在Handle的方法中，只能
                    // 有一个方法，就是handleMessage，于是，他现在就调用这个方法，
                    // 开始替换 TextView中的值。
                 //   handler.sendMessage(message);
                    handler.postDelayed(myRunnable,1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText("update the thead");
//                        }
//                    });
//                    //textView.setText("update the thead");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
    }
    }

