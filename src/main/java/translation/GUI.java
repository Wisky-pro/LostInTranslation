package translation;

import javax.swing.*;
import java.awt.event.*;

// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JComboBox<String> languages;
            JScrollPane countries;
            JList<String> countriesList;

            /* Setup */

            JSONTranslator translator = new JSONTranslator();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();
            CountryCodeConverter countryConverter = new CountryCodeConverter();

            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (String country : translator.getCountryCodes()) {
                String converted = countryConverter.fromCountryCode(country);
                listModel.addElement(converted);
            }

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (String language : translator.getLanguageCodes()) {
                String converted = languageConverter.fromLanguageCode(language);
                model.addElement(converted);
            }

            countriesList = new JList<>(listModel);
            countries = new JScrollPane(countriesList);

            languages = new JComboBox<>(model);
            /* */

            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new BoxLayout(countryPanel, BoxLayout.Y_AXIS));

            countryPanel.add(countries);

            JPanel languagePanel = new JPanel();

            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languages);

            JPanel buttonPanel = new JPanel();

            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = (String) languages.getSelectedItem();
                    System.out.println(language);

                    String country = countriesList.getSelectedValue();
                    System.out.println(country);

                    JSONTranslator json_translator =  new JSONTranslator();
                    CountryCodeConverter converter = new CountryCodeConverter();
                    LanguageCodeConverter code_converter = new LanguageCodeConverter();

                    String result = json_translator.translate(country, language);
                    if (result == null) {
                       result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
