using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Gradus
{
    public class MeasureItem
    {
        public enum MeasureType
        {
            Degrees,
            Radians,
            Sexagesimal
        }

        public MeasureType type;
        public double value;

        public MeasureItem(string raw = null)
        {
            if (raw == null || !double.TryParse(raw, out this.value))
            {
                this.value = 0;
            }
        }
    }
}
