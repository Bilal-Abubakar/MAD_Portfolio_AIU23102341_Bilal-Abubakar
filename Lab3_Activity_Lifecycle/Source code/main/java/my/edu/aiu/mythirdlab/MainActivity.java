// Your package name should be correct here
package my.edu.aiu.mythirdlab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {

    // TextViews for Formula and Result
    private TextView formulaTextView;
    private TextView resultTextView;
    private final StringBuilder formulaBuilder = new StringBuilder();

    // Declare all button variables
    private Button button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9;
    private Button button_dot, button_add, button_subtract, button_multiply, button_divide, button_equals;
    private Button button_clear, button_open_bracket, button_close_bracket;

    // Flag to choose between exp4j and Rhino (true = Rhino, false = exp4j)
    private final boolean useRhino = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        formulaTextView = findViewById(R.id.formulaTextView);
        resultTextView = findViewById(R.id.resultTextView);

        // Initialize all buttons by finding them in the layout
        initializeButtons();

        // Set listeners for all the initialized buttons
        setButtonClickListeners();
    }

    private void initializeButtons() {
        // --- This section directly matches your XML IDs ---
        button_0 = findViewById(R.id.button_0);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_4 = findViewById(R.id.button_4);
        button_5 = findViewById(R.id.button_5);
        button_6 = findViewById(R.id.button_6);
        button_7 = findViewById(R.id.button_7);
        button_8 = findViewById(R.id.button_8);
        button_9 = findViewById(R.id.button_9);

        button_dot = findViewById(R.id.button_dot);
        button_add = findViewById(R.id.button_add);
        button_subtract = findViewById(R.id.button_subtract);
        button_multiply = findViewById(R.id.button_multiply);
        button_divide = findViewById(R.id.button_divide);

        button_equals = findViewById(R.id.button_equals);

        button_clear = findViewById(R.id.button_clear);
        button_open_bracket = findViewById(R.id.button_open_bracket);
        button_close_bracket = findViewById(R.id.button_close_bracket);
        // --- End of matching section ---
    }

    private void setButtonClickListeners() {
        // A common listener for most buttons to append their text to the formula
        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            String buttonText = button.getText().toString();

            // Handle the "( )" button - just add "("
            if (buttonText.equals("( )")) {
                formulaBuilder.append("(");
            } else {
                formulaBuilder.append(buttonText);
            }

            updateFormulaText();
        };

        // Assign the listener to all number, operator, and parenthesis buttons
        button_0.setOnClickListener(listener);
        button_1.setOnClickListener(listener);
        button_2.setOnClickListener(listener);
        button_3.setOnClickListener(listener);
        button_4.setOnClickListener(listener);
        button_5.setOnClickListener(listener);
        button_6.setOnClickListener(listener);
        button_7.setOnClickListener(listener);
        button_8.setOnClickListener(listener);
        button_9.setOnClickListener(listener);

        button_dot.setOnClickListener(listener);
        button_add.setOnClickListener(listener);
        button_subtract.setOnClickListener(listener);
        button_multiply.setOnClickListener(listener);
        button_divide.setOnClickListener(listener);
        button_open_bracket.setOnClickListener(listener);
        button_close_bracket.setOnClickListener(listener);

        // Specific listener for the "Clear" button
        button_clear.setOnClickListener(v -> {
            formulaBuilder.setLength(0); // Clear the formula
            updateFormulaText();
            resultTextView.setText(""); // Clear the result
        });

        // Specific listener for the "Equals" button
        button_equals.setOnClickListener(v -> {
            if (formulaBuilder.length() == 0) {
                return; // Do nothing if formula is empty
            }

            // Choose evaluation method
            if (useRhino) {
                evaluateWithRhino();
            } else {
                evaluateWithExp4j();
            }
        });
    }

    // Evaluate using Rhino JavaScript engine
    @SuppressLint("DefaultLocale")
    private void evaluateWithRhino() {
        Context rhinoContext = null;
        try {
            // Create and enter a Context
            rhinoContext = Context.enter();
            // Set optimization level for Android (-1 = interpreted mode)
            rhinoContext.setOptimizationLevel(-1);

            // Initialize standard objects (gives us access to Math, etc.)
            Scriptable scope = rhinoContext.initStandardObjects();

            // Replace × with * for JavaScript compatibility
            String jsExpression = formulaBuilder.toString().replace("×", "*");

            // Evaluate the expression
            Object result = rhinoContext.evaluateString(scope, jsExpression, "Calculator", 1, null);

            // Convert result to double
            double resultValue = Context.toNumber(result);

            // Display result as integer if it's a whole number, otherwise as double
            if (resultValue == (long) resultValue && !Double.isInfinite(resultValue)) {
                resultTextView.setText(String.format("%d", (long) resultValue));
            } else {
                resultTextView.setText(String.format("%s", resultValue));
            }

        } catch (Exception e) {
            resultTextView.setText("Invalid Calculation");
            e.printStackTrace();
        } finally {
            // Always exit the Context
            if (rhinoContext != null) {
                Context.exit();
            }
        }
    }

    // Evaluate using exp4j library
    private void evaluateWithExp4j() {
        try {
            //  × with * for exp4j compatibility
            String expression = formulaBuilder.toString().replace("×", "*");

            // Use exp4j library to evaluate the formula string
            Expression exp = new ExpressionBuilder(expression).build();
            double result = exp.evaluate();

            // Display result as integer if it's a whole number, otherwise as double
            if (result == (long) result) {
                resultTextView.setText(String.format("%d", (long) result));
            } else {
                resultTextView.setText(String.format("%s", result));
            }
        } catch (Exception e) {
            resultTextView.setText("Invalid Calculation");
            e.printStackTrace();
        }
    }

    // Helper method to update the formula TextView
    private void updateFormulaText() {
        formulaTextView.setText(formulaBuilder.toString());
    }
}