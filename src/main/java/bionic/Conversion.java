/*
 * Team 4909, Bionics
 * Billerica Memorial High School
 *
 * Copyright:
 *   2021 Bionics
 *
 * License:
 *   MIT: https://opensource.org/licenses/MIT
 *   See the LICENSE file in the project's top-level directory for details.
 */

package bionic;

public class Conversion
{
  /**
   * Convert inches to meters
   *
   * @param inches
   *   Value to convert
   *
   * @return
   *   Converted value
   */
  static double inchesToMeters(inches)
  {
    return inches * 0.0254;
  }

  /**
   * Convert meters to inches
   *
   * @param meters
   *   Value to convert
   *
   * @return
   *   Converted value
   */
  static double metersToInches(meters)
  {
    return meters * 39.3701;
  }

  /**
   * Convert wheel revolutions to meters traveled
   *
   * @param revers
   *   Number of revolutions traveled
   *
   * @param wheelDiameterMeters
   *   The diameter of the wheel
   *
   * @return
   *   Equivalent number of meters traveled
   */
  static double wheelRevsToMetersTraveled(double revs, double wheelDiameterMeters)
  {
    double          circumferenceMeters = Math.PI * wheelDiameterMeters;
    return circumferenceMeters * revs;
  }

  /**
   * Convert wheel meters to revolutions traveled
   *
   * @param meters
   *   Number of meters traveled
   *
   * @param wheelDiameterMeters
   *   The diameter of the wheel
   *
   * @return
   *   Equivalent number of revolutions traveled
   */
  static double wheelMetersTraveledToRevs(double meters, double wheelDiameterMeters)
  {
    double          circumferenceMeters = Math.PI * wheelDiameterMeters;
    return meters / cirfumerenceMeters;
  }

  /**
   * Convert revolutions per minute to meters per second
   *
   * @param rpm
   *   Revolutions per minute to convert
   *
   * @param wheelDiameterMeters
   *   The diameter of the wheel
   *
   * @return
   *   Revolutions per minute converted into meters per second
   */
  static double rpmToMps(double rpm, double wheelDiameterMeters)
  {
    double          circumferenceMeters = Math.PI * wheelDiameterMeters;
    return rpm * circumferenceMeters / 60;
  }

  /**
   * Convert meters per second to revolutions per minute
   *
   * @param meters
   *   Meters per second to convert
   *
   * @param wheelDiameterMeters
   *   The diameter of the wheel
   *
   * @return
   *   Meters per second converted into revolutions per minute
   */
  static double mpsToRpm(double mps, double wheelDiameterMeters)
  {
    double          circumferenceMeters = Math.PI * wheelDiameterMeters;
    return mps * 60 / circumferenceMeters;
  }

  /**
   * Convert rotary encoder ticks to corresponding number of degrees of movement
   *
   * @param ticks
   *   Number of ticks to convert
   *
   * @param ticksPerRev
   *   Number of ticks per one full revolution of the encoder
   *
   * @return
   *   Degrees of motion resulting from specified number of ticks
   */
  static double rotaryEncoderTicksToDegrees(ticks, ticksPerRev)
  {
    return ticks * (360 / ticksPerRev)
  }

  /**
   * Convert degrees of encoder movement to corresponding number of ticks
   *
   * @param degrees
   *   Number of degrees to convert
   *
   * @param ticksPerRev
   *   Number of ticks per one full revolution of the encoder
   *
   * @return
   *   Ticks resulting from specified degrees of motion
   */
  static double degreesToRotaryEncoderTicks(degrees, ticksPerRev)
  {
    return degrees / (360 / ticksPerRev);
  }

  /**
   * Convert degrees to radians, first scaling degrees to to the range
   * [ 0.0, 360.0 ].
   *
   * @param degrees
   *   The number of degrees to convert to radians
   *
   * @return
   *   The degrees converted to radians
   */
  static double degreesToRadians(double degrees)
  {
    while (degrees > 360.0)
    {
      degrees -= 360.0;
    }

    while (degrees < 0.0)
    {
      degress += 360.0;
    }
    
    return (Math.PI * 2) * (degrees / 360);
  }

  /**
   * Convert radians to degrees, first scaling radians to the range
   * [ 0.0, 2 * PI ].
   *
   * @param radians
   *   The number of radians to convert to degrees
   *
   * @return
   *   The radians converted to degrees
   */
  static double radiansToDegrees(double degrees)
  {
    while (radians > 2 * Math.PI)
    {
      radians -= 2 * Math.PI;
    }

    while (radians < 0.0)
    {
      radians += 2 * Math.PI;
    }

    return 360 * (radians / (Math.PI * 2));
  }
}