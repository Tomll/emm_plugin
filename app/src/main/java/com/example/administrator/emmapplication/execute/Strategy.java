package com.example.administrator.emmapplication.execute;

import com.example.administrator.emmapplication.entity.LimitStrategyEntity;
import com.example.administrator.emmapplication.utils.GsonUtils;
import com.example.administrator.emmapplication.utils.UpdateStrategyChangeUtils;
import com.example.administrator.emmapplication.utils.passsword.DefaultPwd;
import com.example.administrator.emmapplication.utils.passsword.MixedPwd;

import static android.security.KeyStore.getApplicationContext;
import org.json.JSONException;
import org.json.JSONObject;


public class Strategy extends ExecuteOption {
    public Strategy(JSONObject data) {
        super(data);
    }

    @Override
    public void action() {
        try {
            //取到上一次策略（若是第一次下发策略，则得到的是策略的默认值）
            LimitStrategyEntity oldStrategy = (LimitStrategyEntity)
                    GsonUtils.getJsonToEntity(getApplicationContext(),1,
                            UpdateStrategyChangeUtils.fileName,LimitStrategyEntity.class);

            JSONObject password = data.getJSONObject("password");
            JSONObject restriction = data.getJSONObject("restriction");
            JSONObject encrytion = data.getJSONObject("encrytion");

            //config_flag表示该类是否进行了策略配置，0表示未配置、1表示已配置
            passwordConfig(password);
            RestrictionStrategy.getInstance(oldStrategy.getRestriction(),restriction).executeLimitStrategy();
            // TODO Something encrytion

            saveStrategy();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void passwordConfig(JSONObject password) throws JSONException {
        DefaultPwd pwd1 = new DefaultPwd(getApplicationContext());
        int pwComplexity = password.getInt("pwComplexity");
        MixedPwd pwd2 = new MixedPwd(getApplicationContext());
        //如果是复杂密码的话额外添加复杂密码的配置项
        if(2==pwComplexity){
            pwd1.setPwdConfig(pwd2);
        }
        pwd1.pwdConfigs(getApplicationContext(),password);
    }

    private void saveStrategy(){
        UpdateStrategyChangeUtils.saveNewLimitStrategy(getApplicationContext(),data);
    }
}
