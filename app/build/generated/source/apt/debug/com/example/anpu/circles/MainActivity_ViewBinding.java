// Generated code from Butter Knife. Do not modify!
package com.example.anpu.circles;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.CharSequence;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131230782;

  private TextWatcher view2131230782TextWatcher;

  private View view2131230780;

  private TextWatcher view2131230780TextWatcher;

  private View view2131230811;

  private View view2131230862;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.edit_username, "field 'userEditText' and method 'usernameChanged'");
    target.userEditText = Utils.castView(view, R.id.edit_username, "field 'userEditText'", EditText.class);
    view2131230782 = view;
    view2131230782TextWatcher = new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {
        target.usernameChanged(p0, p1, p2, p3);
      }

      @Override
      public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
      }

      @Override
      public void afterTextChanged(Editable p0) {
      }
    };
    ((TextView) view).addTextChangedListener(view2131230782TextWatcher);
    view = Utils.findRequiredView(source, R.id.edit_pwd, "field 'pwdEditText' and method 'pwdChanged'");
    target.pwdEditText = Utils.castView(view, R.id.edit_pwd, "field 'pwdEditText'", EditText.class);
    view2131230780 = view;
    view2131230780TextWatcher = new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {
        target.pwdChanged(p0, p1, p2, p3);
      }

      @Override
      public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
      }

      @Override
      public void afterTextChanged(Editable p0) {
      }
    };
    ((TextView) view).addTextChangedListener(view2131230780TextWatcher);
    view = Utils.findRequiredView(source, R.id.login_button, "field 'loginButton' and method 'loginClicked'");
    target.loginButton = Utils.castView(view, R.id.login_button, "field 'loginButton'", Button.class);
    view2131230811 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.loginClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.signup_button, "field 'signupButton' and method 'signupClicked'");
    target.signupButton = Utils.castView(view, R.id.signup_button, "field 'signupButton'", Button.class);
    view2131230862 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.signupClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userEditText = null;
    target.pwdEditText = null;
    target.loginButton = null;
    target.signupButton = null;

    ((TextView) view2131230782).removeTextChangedListener(view2131230782TextWatcher);
    view2131230782TextWatcher = null;
    view2131230782 = null;
    ((TextView) view2131230780).removeTextChangedListener(view2131230780TextWatcher);
    view2131230780TextWatcher = null;
    view2131230780 = null;
    view2131230811.setOnClickListener(null);
    view2131230811 = null;
    view2131230862.setOnClickListener(null);
    view2131230862 = null;
  }
}
