# Interactive Parallel Coordinates Visualization
This project extends the parallel coordinates visualization with interactive features, including line highlighting and brushing, to allow users to explore multidimensional datasets more intuitively. 
The application supports both the CIS 2012 and CIS 2019 datasets.

## Features
1. Line Highlighting & Tooltips
  - Mouse-Over Highlighting: When hovering over a line (data row), it will:
    - Increase in thickness
    - Change to a different color to stand out
  - Data Tooltips: A tooltip will pop up when hovering, displaying the data values for each axis of the selected line.
2. Brushing with Rubber-Band Selection
  - Rubber-Band Selection: Users can drag a rectangle to select a group of line segments. The selected lines will remain visible and highlighted, while others will become faint or invisible.
  - Iterative Brushing: Users can repeatedly apply brushing to narrow down selections, focusing on specific data ranges across multiple dimensions.
## Technologies Used
- Java Swing: For GUI and mouse interaction.
- JDBC: To retrieve the datasets from the embedded Derby database.
- JFreeChart or custom rendering for the parallel coordinates visualization.
