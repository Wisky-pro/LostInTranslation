package translation;

import javax.swing.*;
import java.awt.event.*;

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

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            countriesList.addListSelectionListener(e -> {
               if (!e.getValueIsAdjusting()) {
                   String country =  countriesList.getSelectedValue();
                   String language = (String) languages.getSelectedItem();

                   JSONTranslator json_translator =  new JSONTranslator();
                   CountryCodeConverter country_converter = new CountryCodeConverter();
                   LanguageCodeConverter code_converter = new LanguageCodeConverter();

                   String inputCountry = country_converter.fromCountry(country);
                   String inputLanguage = code_converter.fromLanguage(language);

                   String result = json_translator.translate(inputCountry, inputLanguage);
                   if (result == null) {
                       result = "no translation found!";
                   }
                   resultLabel.setText(result);
               }
            });

            languages.addActionListener(e -> {
                String country = countriesList.getSelectedValue();
                String language = (String) languages.getSelectedItem();

                JSONTranslator json_translator =  new JSONTranslator();
                CountryCodeConverter country_converter = new CountryCodeConverter();
                LanguageCodeConverter code_converter = new LanguageCodeConverter();

                String inputCountry = country_converter.fromCountry(country);
                String inputLanguage = code_converter.fromLanguage(language);

                String result = json_translator.translate(inputCountry, inputLanguage);
                if (result == null) {
                    result = "no translation found!";
                }

                resultLabel.setText(result);
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
