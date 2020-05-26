using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Text.RegularExpressions;

namespace Gradus
{
    /// <summary>
    /// Lógica de interacción para MainWindow.xaml
    /// 
    /// TODO: implementar MVVM en un futuro...
    /// </summary>
    public partial class MainWindow : Window, INotifyPropertyChanged
    {
        public event PropertyChangedEventHandler PropertyChanged;

        // bandera para saber si el usuario hizo su primera transformación
        private bool firstTimeResult = false;

        private int decimalsLimite = 3;
        private double lastDegreesResult = 0;
        private Visibility canViewDetails = Visibility.Collapsed;

        public Visibility CanViewDetails
        {
            get { return canViewDetails; }
            set
            {
                canViewDetails = value;
                this.NotifyPropertyChanged("CanViewDetails");
            }
        }

        private MeasureItem.MeasureType measureInputType = MeasureItem.MeasureType.Degrees;
        private MeasureItem.MeasureType measureOutputType = MeasureItem.MeasureType.Radians;

        private string _measureInputValue;
        public string MeasureInputValue
        {
            get { return _measureInputValue; }
            set
            {
                _measureInputValue = value;
                this.NotifyPropertyChanged("MeasureInputValue");
            }
        }

        private string _measureOutputValue;
        public string MeasureOutputValue
        {
            get { return _measureOutputValue; }
            set
            {
                _measureOutputValue = value;
                this.NotifyPropertyChanged("MeasureOutputValue");
            }
        }

        public MainWindow()
        {
            InitializeComponent();

            DataContext = this;

            measureInputTypeContainer
                .Children
                .OfType<RadioButton>()
                .First()
                .RaiseEvent(new RoutedEventArgs(RadioButton.CheckedEvent));

            measureOutputTypeContainer
                .Children
                .OfType<RadioButton>()
                .First(r => r.IsEnabled)
                .RaiseEvent(new RoutedEventArgs(RadioButton.CheckedEvent));
        }

        /// <sumary>
        /// seleccionamos el tipo de medida
        /// </sumary>
        private void MeasureChooseType(object sender, RoutedEventArgs e)
        {
            var choosed = sender as RadioButton;
            string tag = "";

            // el contenedor aun no esta listo ( view not ready )
            if (measureOutputTypeContainer == null)
            {
                return;
            }

            // obtenemos la etiqueta de la medida selccionada
            if (choosed.Tag != null)
            {
                tag = choosed.Tag.ToString();
            }

            if (String.Equals(choosed.GroupName, "measureInputType"))
            {
                var buttons = measureOutputTypeContainer.Children.OfType<RadioButton>();
                var disabled = buttons.First(r => String.Equals(r.Tag, choosed.Tag));

                if (String.Equals(tag, "Sexagesimal"))
                {
                    sexagesimalInputs.Visibility = Visibility.Visible;
                    MeasureInputTextBox.Visibility = Visibility.Collapsed;
                }
                else
                {
                    sexagesimalInputs.Visibility = Visibility.Collapsed;
                    MeasureInputTextBox.Visibility = Visibility.Visible;
                }

                foreach (var button in buttons)
                {
                    button.IsEnabled = true;
                }

                var wasChecked = disabled.IsChecked ?? false;
                disabled.IsEnabled = false;
                disabled.IsChecked = false;

                if (wasChecked)
                {
                    buttons.First(r => r.IsEnabled).IsChecked = true;
                }

                Enum.TryParse(tag, out measureInputType);
            }
            else
            {
                Enum.TryParse(tag, out measureOutputType);
            }

            checkTransformAvailability();
        }

        private void NotifyPropertyChanged(string propertyname)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyname));
            }
        }

        /// <sumary>
        /// verificamos que sea posible transformar una medida a otra
        /// </sumary>
        private void checkTransformAvailability()
        {
            double value;
            var control = MeasureInputTextBox;
            String text = control.Text.Trim();
            Regex fractionRegex = new Regex(@"^([1-9][0-9]*)\/([1-9][0-9]*)$");

            if (TransformButton == null)
            {
                return;
            }

            if (measureInputType == MeasureItem.MeasureType.Sexagesimal)
            {
                bool valid = true;
                string degreesText = sexagesimalDegrees.Text.Trim();
                string minutesText = sexagesimalMinutes.Text.Trim();
                string secondsText = sexagesimalSeconds.Text.Trim();
                int intDummyValue;
                double doubleDummyValue;

                secondsText = secondsText.Replace(".", ",");

                if (String.IsNullOrEmpty(degreesText)
                    || String.IsNullOrWhiteSpace(minutesText)
                    || String.IsNullOrWhiteSpace(secondsText))
                {
                    valid = false;
                }
                else
                {
                    valid = int.TryParse(degreesText, out intDummyValue)
                        && int.TryParse(minutesText, out intDummyValue)
                        && Double.TryParse(secondsText, out doubleDummyValue);
                }

                TransformButton.IsEnabled = valid;
                return;
            }

            TransformButton.IsEnabled = text.Length > 0
                    && ((measureInputType != MeasureItem.MeasureType.Sexagesimal
                        && fractionRegex.IsMatch(text)) || double.TryParse(text, out value));
        }

        private void MeasureInputTextBoxTyping(object sender, KeyEventArgs e)
        {
            checkTransformAvailability();
        }

        private void Tranform(object sender, RoutedEventArgs e)
        {
            String measureValue = MeasureInputTextBox.Text.Trim();
            double value = 0;
            double degrees = 0;

            measureValue = measureValue.Replace(".", ",");
            Regex fractionRegex = new Regex(@"^([1-9][0-9]*)\/([1-9][0-9]*)$");

            if (
                measureInputType != MeasureItem.MeasureType.Sexagesimal
                && (!double.TryParse(measureValue, out value) 
                    && !fractionRegex.IsMatch(measureValue))
                )
            {
                return;
            }

            if (measureInputType != MeasureItem.MeasureType.Sexagesimal && fractionRegex.IsMatch(measureValue))
            {
                var matches = fractionRegex.Matches(measureValue);
                int numerator;
                int denominator;

                if(!int.TryParse(matches[0].Groups[1].Value, out numerator)
                    || !int.TryParse(matches[0].Groups[2].Value, out denominator))
                {
                    value = 0;
                }
                else
                {
                    value = (double)numerator / (double)denominator;
                }

            }

            double output = 0;

            if (measureInputType == MeasureItem.MeasureType.Degrees)
            {
                degrees = value;

                // grados a radianes
                if (measureOutputType == MeasureItem.MeasureType.Radians)
                {
                    output = value * (Math.PI / 180);
                }
                // grados a sexagesimales
                else if (measureOutputType == MeasureItem.MeasureType.Sexagesimal)
                {                    
                    MeasureOutputTextBox.Text = ParseToSexagesimal(value);
                }
            }
            else if (measureInputType == MeasureItem.MeasureType.Radians)
            {
                degrees = value * (180 / Math.PI);

                if (measureOutputType == MeasureItem.MeasureType.Degrees)
                {
                    output = degrees;
                }
                // radianes a sexagesimales
                else if (measureOutputType == MeasureItem.MeasureType.Sexagesimal)
                {
                    MeasureOutputTextBox.Text = ParseToSexagesimal(degrees);
                }
            }
            // sexagesimal a grados o radianes
            else
            {
                int _degrees = 0;
                int minutes = 0;
                double seconds = 0.00;

                try
                {
                    _degrees = int.Parse(sexagesimalDegrees.Text.Trim());
                    minutes = int.Parse(sexagesimalMinutes.Text.Trim());
                    seconds = double.Parse(sexagesimalSeconds.Text.Trim());
                }
                catch { }

                output = _degrees + ((double)minutes / (double)60) + ((double)seconds / (double)3600);
                degrees = output;

                if (measureOutputType == MeasureItem.MeasureType.Radians)
                {
                    output = output * (Math.PI / 180);
                }
            }

            if ((measureOutputType != MeasureItem.MeasureType.Sexagesimal))
            {
                output = Decimal.ToDouble(Decimal.Round(Convert.ToDecimal(output), decimalsLimite));
                MeasureOutputTextBox.Text = output.ToString();
            }
            
            lastDegreesResult = degrees;
            viewDetailsButton.IsEnabled = true;

            UpdateDetails(degrees);

            if (!firstTimeResult)
            {
                CanViewDetails = Visibility.Visible;
                viewDetailsButton.Content = "Ocultar detalles";
                firstTimeResult = true;
            }
        }

        private void UpdateDetails(double degrees)
        {
            string quadrantText;
            double angle = degrees;

            angle %= 360.0;

            if (angle < 0)
            {
                angle += 360.0;
            }

            var quadrant = (angle / 90) % 4 + 1;

            if ((quadrant % 1) == 0)
            {
                quadrant = (quadrant - 1) <= 0 ? 1 : quadrant - 1;
            }

            if (quadrant < 2)
            {
                quadrantText = "I";
            }
            else if (quadrant >= 2 && quadrant < 3)
            {
                quadrantText = "II";
            }
            else if (quadrant >= 3 && quadrant < 4)
            {
                quadrantText = "III";
            }
            else
            {
                quadrantText = "IV";
            }

            quadrantDetails.Content = quadrantText;

            lapsDetails.Content = Decimal.Round((Decimal)degrees / 360, decimalsLimite);
            roundedLapsDetails.Content = Convert.ToString(
                Math.Truncate(Convert.ToDecimal(degrees / 360)));

            string[] sexagesimals = DegreesToSexagesimals(degrees);

            degreesDetails.Content = Math.Round(degrees, decimalsLimite);
            minutesDetails.Content = sexagesimals[1];
            secondsDetails.Content = sexagesimals[2];

            piradsDetails.Content = String.Format("{0} π rads",
                Decimal.Round((Decimal)degrees / (Decimal)180, decimalsLimite));

            sinDetails.Content = Math.Round(Math.Sin(degrees * (Math.PI / 180)), decimalsLimite);
            cosDetails.Content = Math.Round(Math.Cos(degrees * (Math.PI / 180)), decimalsLimite);

            if (degrees == 90 || degrees == 270)
            {
                tanDetails.Content = "∞";
            }
            else
            {
                tanDetails.Content =  Math.Round(Math.Tan(degrees * (Math.PI / 180)), decimalsLimite);
            }
        }

        private string[] DegreesToSexagesimals(double value)
        {
            if ((value % 1) == 0)
            {
                return new string[] {
                    Convert.ToString(value),
                    "0",
                    "0"
                };
            }
            else
            {
                var degrees = Math.Truncate(value);
                var minutes = (value - Math.Truncate(value)) * 60;
                double seconds;

                if ((minutes % 1) == 0)
                {
                    minutes = (int)minutes;
                    seconds = 0;
                }
                else
                {
                    seconds = (minutes - Math.Truncate(minutes)) * 60;
                    minutes = Math.Truncate(minutes);
                }

                var roundSeconds = Math.Round(seconds, 2);

                if ((roundSeconds % 1) == 0)
                {
                    seconds = (int)seconds;
                }
                else
                {
                    seconds = roundSeconds;
                }

                return new string[]
                {
                    Convert.ToString(degrees),
                    Convert.ToString(minutes),
                    Convert.ToString(seconds),
                };
            }
        }

        /// <sumary>
        /// transformamos a una cadena sexagesimal desde grados
        /// </sumary>
        private string ParseToSexagesimal(double value)
        {
            var data = DegreesToSexagesimals(value);
            var degrees = data[0];
            var minutes = data[1];
            var seconds = data[2];

            return String.Format(
                "{0}° {1}' {2}\"",
                degrees,
                minutes,
                seconds
            );
        }

        private void DecimalSlideChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            var control = sender as Slider;

            decimalsLimite = Convert.ToInt32(control.Value);
            decimalsLimiteLabel.Content = Convert.ToString(decimalsLimite);

            if (TransformButton != null)
            {
                TransformButton.RaiseEvent(new RoutedEventArgs(Button.ClickEvent));
            }
        }

        private void viewDetailsButtonClick(object sender, RoutedEventArgs e)
        {
            CanViewDetails = CanViewDetails == Visibility.Visible
                ? Visibility.Collapsed
                : Visibility.Visible;

            if (CanViewDetails == Visibility.Collapsed)
            {
                viewDetailsButton.Content = "Ver detalles";
            }
            else
            {
                viewDetailsButton.Content = "Ocultar detalles";
            }
        }

        private void ShowAbout(object sender, RoutedEventArgs e)
        {
            var aboutWindow = new AboutWindow();
            aboutWindow.ShowInTaskbar = false;
            aboutWindow.Owner = this;
            aboutWindow.ShowDialog();
        }
    }
}
