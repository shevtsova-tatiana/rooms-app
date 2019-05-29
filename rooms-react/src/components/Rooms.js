import React from "react";
import SelectedListItem from "./SelectedListItem";
import LightOnFalse from "../lightbulb.svg";
import LightOnTrue from "../lightbulbon.svg";
import {Button, withStyles} from "@material-ui/core";
import axios from "axios";


const styles = ({
        main: {
            // spacing: '36',
            display: 'inline-flex',
            // background: 'yellow',
        },
        bulbContainer: {
            position: 'absolute',
            top: 60,
            left: 125,
            // position: 'absolute',
            // top: '50%',
            // left: '50%',
            // width: '30em',
            // height: '18em',
            // margintop: '-9em', /*set to a negative number 1/2 of your height*/
            // marginleft: '-15em', /*set to a negative number 1/2 of your width*/
        },
        button: {
            padding: '5px 15px',
            border: 'inherit',
            font: 'inherit',
            color: 'inherit',
            background: 'transparent',
            cursor: 'pointer',
            margin: '15px 15px',
        },
        paper: {
            height: '170px',
            width: '160px',
            background: 'lightGreen',
        },
        column: {
            display: 'flex',
        },
    })
;

class Rooms extends React.Component {

    constructor(props) {
        super(props);
        this.state = {idList: [], selectedRoomId: 0, lightState: false,};
    }

    handleRoomClick = (event, id) => {
        this.setState({selectedRoomId: id});
        if (this.eventSource !== undefined)
            this.eventSource.close();
        axios.get('/api/' + id)
            .then(hodor => {

                this.eventSource = new EventSource('/api/notifications/' + id);
                this.eventSource.onmessage = message => {
                    console.log(JSON.parse(message.data));
                    this.setState({lightState: JSON.parse(message.data).lightOn});
                };

                this.setState({lightState: hodor.data.lightOn});
            })
    };

    handleSwitchClick = (event, selectedRoomId) => {
        axios.post('/api/' + selectedRoomId);
    };

    handleExitClick = (event) => {
        this.setState({selectedRoomId: 0});
        if (this.eventSource !== undefined)
            this.eventSource.close();
    };

    componentWillMount() {
        axios.get('/api/id_list')
            .then(hodor => {
                    this.setState({idList: hodor.data});
                }
            )
    }

    render() {
        const {classes} = this.props;

        return (
            <div>
                <div style={styles.main}>
                    <SelectedListItem idList={this.state.idList} selectedRoomId={this.state.selectedRoomId}
                                      handleRoomClick={this.handleRoomClick.bind(this)}/>
                    {
                        this.state.selectedRoomId === 0 ? <div/> : (this.state.lightState ?
                            <img src={LightOnTrue} alt={'Light ON'}/> :
                            <img src={LightOnFalse} alt={'Light OFF'}/>)

                    }
                </div>
                < div>
                    < Button variant="contained" size="medium" color="secondary" className={classes.button}
                             onClick={event => this.handleExitClick(event)}>
                        Exit
                    </Button>
                    <Button variant="contained" size="medium" color="primary" className={classes.button}
                            onClick={event => this.handleSwitchClick(event, this.state.selectedRoomId)}
                            disabled={this.state.selectedRoomId === 0}>
                        Switch
                    </Button>
                </div>
            </div>
        )
    }
}

// Rooms.propTypes = {
//     classes: PropTypes.object.isRequired,
// };

export default withStyles(styles)(Rooms);