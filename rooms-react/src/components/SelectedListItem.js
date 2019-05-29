import React from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';

const classes = ({
    root: {
        width: '100%',
        maxWidth: 160,
        backgroundColor: 'white',
        margin: '0px 16px 16px 0px',
    },
    paper: {
        padding: '5px',
    },
});

class SelectedListItem extends React.Component {


    render() {
        const {classes, idList, selectedRoomId, handleRoomClick} = this.props;

        return (
            <div className={classes.root}>
                <List className={classes.paper}>
                    {
                        idList.map((id) => (
                            <ListItem key={id}
                                      button
                                      selected={selectedRoomId === id}
                                      onClick={event => handleRoomClick(event, id)}
                            >
                                <ListItemText primary={id}/>
                            </ListItem>
                        ))
                    }
                </List>
            </div>
        );
    }
}

SelectedListItem.propTypes = {
    classes: PropTypes.object.isRequired,
    idList: PropTypes.array.isRequired,
    selectedRoomId: PropTypes.number.isRequired,
    handleRoomClick: PropTypes.func.isRequired,
};

export default withStyles(classes)(SelectedListItem);
