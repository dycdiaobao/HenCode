package wwlw.citic.com.a10_layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str = "2.订阅累计超过500元，俺搜提供开票服务，可(前往开票)";

        System.out.println(str.replace("()",""));
    }
}
